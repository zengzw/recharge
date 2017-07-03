package com.tsh.broker.execute.sdm.ofpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.tsh.broker.config.OfPayConfig;
import com.tsh.broker.enumType.LogTypeEnum;
import com.tsh.broker.enumType.OfpayEnum;
import com.tsh.broker.execute.sdm.SdmExecute;
import com.tsh.broker.po.AreaMappingInfo;
import com.tsh.broker.service.PayUnitService;
import com.tsh.broker.service.ProcessingLogService;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.RandomUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.PaymentBill;
import com.tsh.broker.vo.sdm.request.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * SdmOfPayExecute
 *
 * @author dengjd
 * @date 2016/10/15
 */
@SuppressWarnings("all")
public class SdmOfPayExecute  implements SdmExecute {
    private static Logger logger = LogManager.getLogger(SdmOfPayExecute.class);

    @Autowired
    private PayUnitService payUnitService;
    @Autowired
    private ProcessingLogService processingLogService;
    private OfPayConfig ofPayConfig;
    private SdmRequestBuilder sdmRequestBuilder;
    private SdmResponseParser sdmResponseParser;

    public SdmOfPayExecute(OfPayConfig ofPayConfig){
        this.ofPayConfig = ofPayConfig;
        this.sdmRequestBuilder = new SdmRequestBuilder(ofPayConfig);
        this.sdmResponseParser = new SdmResponseParser();
    }

    @Override
    public ResponseWrapper<List<PayUnit>> queryPayUnit(PayUnitRequest payUnitRequest) throws Exception {
        if (payUnitRequest == null)
            throw new  IllegalArgumentException("payUnitRequest is null object");

        ResponseWrapper<List<PayUnit>> responseWrapper = new ResponseWrapper<List<PayUnit>>();
        String province = payUnitRequest.getProvince();
        String city = payUnitRequest.getCity();
        AreaMappingInfo areaMappingInfo = payUnitService.findAreaMappingInfoByOrigin(province, city);
        if(areaMappingInfo == null)
            return responseWrapper;

        logger.info("查询缴费单位请求参数:" + JSONObject.toJSONString(payUnitRequest));
        Map<String, String> queryPayUnitParams = sdmRequestBuilder.buildQueryPayUnitRequest(payUnitRequest,areaMappingInfo);

        String  response =  HttpXmlClient.post(ofPayConfig.getGetPayUnitListURL(), queryPayUnitParams);
        logger.info("查询缴费单位返回结果:" + response);
        responseWrapper =  sdmResponseParser.queryPayUnitParse(response);
        List<PayUnit> payUnitList = responseWrapper.getData();

        if(CollectionUtils.isNotEmpty(payUnitList)){
            Iterator<PayUnit> iterator = payUnitList.iterator();
            while (iterator.hasNext()){
                PayUnit payUnit = iterator.next();
                payUnit.setProvince(areaMappingInfo.getTargetProvince());
                payUnit.setCity(areaMappingInfo.getTargetCity());
                payUnit.setPayType(payUnitRequest.getPayType());
                //查询账户类型,固定使用户号
                payUnit.setAccountType(OfpayEnum.AccountTypeEnum.ACCOUNT_TYPE_NO.getValue());
                String productId = getProductId(payUnit, areaMappingInfo);
                //如果该单位没有支持产品，从缴费单位列表移除
                if(StringUtils.isNotBlank(productId)){
                    payUnit.setProductId(productId);
                }else {
                    iterator.remove();
                }
            }
        }

        return responseWrapper;
    }

    @Override
    public ResponseWrapper recharge(RechargeRequest rechargeRequest) throws Exception {
        if (rechargeRequest == null)
            throw new  IllegalArgumentException("rechargeRequest is null object");
        String province = rechargeRequest.getProvince();
        String city = rechargeRequest.getCity();
        AreaMappingInfo areaMappingInfo = payUnitService.findAreaMappingInfoByOrigin(province, city);
        if(areaMappingInfo == null)
            throw new  IllegalArgumentException("映射区域信息不存在");

        logger.info("充值请求参数:" + JSONObject.toJSONString(rechargeRequest));
        Map<String, String> rechargeParams = sdmRequestBuilder.buildRechargeRequest(rechargeRequest,areaMappingInfo);

        String msgId =  processingLogService.writeProcessLog(logger,"recharge", LogTypeEnum.LOG_HTTP,"请求欧飞充值接口:" + JSONObject.toJSONString(rechargeParams));
        String  response =  HttpXmlClient.post(ofPayConfig.getUtilityOrderURL(), rechargeParams);
        processingLogService.writeProcessLog(logger,"recharge", LogTypeEnum.LOG_HTTP,"欧飞充值接口返回:" + response,msgId);

        return sdmResponseParser.rechargeParse(response);
    }

    @Override
    public ResponseWrapper<Integer> queryRechargeResult(RechargeResultRequest rechargeResultRequest) throws Exception {
        if (rechargeResultRequest == null)
            throw new  IllegalArgumentException("rechargeResultRequest is null object");

        logger.info("查询充值结果请求参数:" + JSONObject.toJSONString(rechargeResultRequest));
        Map<String, String> rechargeParams = sdmRequestBuilder.buildQueryRechargeResultRequest(rechargeResultRequest);

        String  response =  HttpXmlClient.get(ofPayConfig.getQueryOrderResultURL(), rechargeParams);
        logger.info("查询充值结果返回结果:" + response);

        return sdmResponseParser.queryRechargeResultParse(response);
    }

    @Override
    public ResponseWrapper resultNotify(ResultNotifyRequest resultNotifyRequest) throws Exception {
        if (resultNotifyRequest == null)
            throw new  IllegalArgumentException("resultNotifyRequest is null object");

        logger.info("充值结果请求参数:" + JSONObject.toJSONString(resultNotifyRequest));
        Map<String, String> resultNotifyParams = sdmRequestBuilder.buildResultNotifyRequest(resultNotifyRequest);

        String msgId = processingLogService.writeProcessLog(logger, "resultNotify", LogTypeEnum.LOG_HTTP, "通知内部订单服务请求参数:" + JSONObject.toJSONString(resultNotifyParams));
        String  response =  HttpXmlClient.post(ofPayConfig.getResultNotifyUrl(), resultNotifyParams);
        processingLogService.writeProcessLog(logger,"resultNotify", LogTypeEnum.LOG_HTTP,"通知内部订单服务返回结果:" + response,msgId);

        ResponseWrapper innerResponse = JSONObject.parseObject(response,ResponseWrapper.class);
        if(innerResponse.getStatus() != Result.STATUS_OK){
            throw new RuntimeException("通知内部订单服务失败");
        }
        processingLogService.writeProcessLog(logger,"resultNotify", LogTypeEnum.LOG_HTTP,"通知内部订单服务返回结果:" + response,msgId);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus(0);

        return responseWrapper;
    }

    @Override
    public ResponseWrapper<List<PaymentBill>> queryPaymentBill(PaymentBillRequest paymentBillRequest) throws Exception {
        if (paymentBillRequest == null)
            throw new  IllegalArgumentException("paymentBillRequest is null object");
        //查询缴费产品信息
        ResponseWrapper<List<PaymentBill>> responseWrapper = new ResponseWrapper<List<PaymentBill>>();
        String province = paymentBillRequest.getProvince();
        String city = paymentBillRequest.getCity();
        AreaMappingInfo areaMappingInfo = payUnitService.findAreaMappingInfoByTarget(province, city);
        if(areaMappingInfo == null)
            return responseWrapper;

        //查询账单信息
        Map<String, String> queryBalanceParams = sdmRequestBuilder.buildQueryPaymentBillRequest(paymentBillRequest);

        logger.info("#queryPaymentBill----> request params:"+JSON.toJSONString(queryBalanceParams));
        
        String  queryBalanceResponse =  HttpXmlClient.post(ofPayConfig.getQueryBalanceURL(), queryBalanceParams);
        responseWrapper =  sdmResponseParser.queryPaymentBillParse(queryBalanceResponse, paymentBillRequest);
        logger.info("查询缴费单位返回结果:" + queryBalanceResponse);

        return responseWrapper;
    }

    @Override
    public ResponseWrapper<String> generateOrderNo(GenerateOrderNoRequest generateOrderNoRequest) {
        if (generateOrderNoRequest == null)
            throw new  IllegalArgumentException("generateOrderNoRequest is null object");
        logger.info("生成订单编号请求参数:" + JSONObject.toJSONString(generateOrderNoRequest));

        String outOrderNo = "SP" +  DateUtils.format(new Date(), "yyyyMMddHHmmss")
                + RandomUtils.generateRandomNo(4);
        logger.info("生成订单编号返回结果:" + outOrderNo);
        ResponseWrapper<String> result = new ResponseWrapper<String>();
        result.setData(outOrderNo);

        return result;
    }

    protected Integer getAccountType(PayUnit payUnit,AreaMappingInfo areaMappingInfo) throws Exception {
        if (payUnit == null)
            throw new  IllegalArgumentException("payUnit is null object");
        if (areaMappingInfo == null)
            throw new  IllegalArgumentException("areaMappingInfo is null object");
        logger.info("查询缴费账户类型请求参数:" + JSONObject.toJSONString(payUnit) );
        Map<String, String> payModeUnitParams = sdmRequestBuilder.buildPayModeRequest(payUnit, areaMappingInfo);

        String  response =  HttpXmlClient.post(ofPayConfig.getGetPayModeListURL(), payModeUnitParams);
        logger.info("查询缴费账户类型返回结果:" + response);
        ResponseWrapper<Integer>  responseWrapper =  sdmResponseParser.queryPayModeParse(response);

        return responseWrapper.getData();
    }

    protected String  getProductId(PayUnit payUnit,AreaMappingInfo areaMappingInfo) throws Exception {
        logger.info("查询缴费单位请求参数:" + JSONObject.toJSONString(payUnit));

        Map<String, String> queryClassIdParams = sdmRequestBuilder.buildQueryClassIdRequest(payUnit, areaMappingInfo);
        String  queryClassIdResponse =  HttpXmlClient.post(ofPayConfig.getQueryClassIdURL(), queryClassIdParams);
        ResponseWrapper<String> queryClassIdParseResponse = sdmResponseParser.queryQueryClassIdParse(queryClassIdResponse);

        logger.info("查询缴费单位返回结果:" + queryClassIdResponse);

        return queryClassIdParseResponse.getData();
    }

	@Override
	public ResponseWrapper<List<PaymentBill>> queryPaymentBillMore(
			PaymentBillRequest paymentBillRequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
		
	}


}
