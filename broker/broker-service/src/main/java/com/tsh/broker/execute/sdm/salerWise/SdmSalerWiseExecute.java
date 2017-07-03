package com.tsh.broker.execute.sdm.salerWise;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.tsh.broker.config.SalerWiseConfig;
import com.tsh.broker.execute.sdm.SdmExecute;
import com.tsh.broker.po.SwPayUnit;
import com.tsh.broker.service.PayUnitService;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.RandomUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.PaymentBill;
import com.tsh.broker.vo.sdm.request.GenerateOrderNoRequest;
import com.tsh.broker.vo.sdm.request.PayUnitRequest;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;

/**
 *
 * 易赛水电煤执行器实现类
 *
 * @author dengjd
 * @date 2016/9/28
 */
@SuppressWarnings("all")
public class SdmSalerWiseExecute implements SdmExecute {

    private static Logger logger = LogManager.getLogger(SdmSalerWiseExecute.class);

    @Autowired
    private PayUnitService payUnitService;
    private SalerWiseConfig salerWiseConfig;
    private SdmRequestBuilder sdmRequestBuilder;
    private SdmResponseParser sdmResponseParser;

    public SdmSalerWiseExecute(SalerWiseConfig salerWiseConfig){
        this.salerWiseConfig = salerWiseConfig;
        this.sdmRequestBuilder = new SdmRequestBuilder(salerWiseConfig);
        this.sdmResponseParser = new SdmResponseParser();
    }

    @Override
    public ResponseWrapper<List<PayUnit>> queryPayUnit(PayUnitRequest payUnitRequest) {
        if (payUnitRequest == null)
            throw new  IllegalArgumentException("payUnitRequest is null object");

        List<PayUnit> payUnitList  = payUnitService.findEnableSwPayUnitList(
                payUnitRequest.getProvince(),
                payUnitRequest.getCity(),
                payUnitRequest.getPayType());
        ResponseWrapper<List<PayUnit>> result = new ResponseWrapper<List<PayUnit>>();
        result.setData(payUnitList);

        return result;
    }

    @Override
    public ResponseWrapper recharge(RechargeRequest rechargeRequest) throws Exception {
        if (rechargeRequest == null)
            throw new  IllegalArgumentException("rechargeRequest is null object");

        logger.info("充值请求参数:" + JSONObject.toJSONString(rechargeRequest));
        Map<String, String> rechargeParams = sdmRequestBuilder.buildRechargeRequest(rechargeRequest);

        String  response =  HttpXmlClient.post(salerWiseConfig.getRechargeUrl(), rechargeParams);
        logger.info("充值返回结果:" + response);

        return sdmResponseParser.rechargeParse(response);
    }

    @Override
    public ResponseWrapper<Integer> queryRechargeResult(RechargeResultRequest rechargeResultRequest) throws Exception {
        if (rechargeResultRequest == null)
            throw new  IllegalArgumentException("rechargeResultRequest is null object");

        logger.info("查询充值结果请求参数:" + JSONObject.toJSONString(rechargeResultRequest));
        Map<String, String> rechargeParams = sdmRequestBuilder.buildQueryRechargeResultRequest(rechargeResultRequest);

        String  response =  HttpXmlClient.post(salerWiseConfig.getQueryRechargeResultUrl(), rechargeParams);
        logger.info("查询充值结果返回结果:" + response);

        return sdmResponseParser.queryRechargeResultParse(response);
    }

    @Override
    public ResponseWrapper resultNotify(ResultNotifyRequest resultNotifyRequest) throws Exception {
        if (resultNotifyRequest == null)
            throw new  IllegalArgumentException("resultNotifyRequest is null object");

        logger.info("充值结果请求参数:" + JSONObject.toJSONString(resultNotifyRequest));
        Map<String, String> resultNotifyParams = sdmRequestBuilder.buildResultNotifyRequest(resultNotifyRequest);
        
        logger.info("通知结果参数=====：" + resultNotifyParams);
        
        String  response =  HttpXmlClient.post(salerWiseConfig.getResultNotifyUrl(), resultNotifyParams);
        logger.info("充值结果返回结果:" + response);

        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus(0);

        return responseWrapper;
    }

    @Override
    public ResponseWrapper<List<PaymentBill>> queryPaymentBill(PaymentBillRequest paymentBillRequest) throws Exception {
        if (paymentBillRequest == null)
            throw new  IllegalArgumentException("paymentBillRequest is null object");

        logger.info("账单查询请求参数:" + JSONObject.toJSONString(paymentBillRequest));
        Map<String, String> queryPaymentBillParams = sdmRequestBuilder.buildQueryPaymentBillRequest(paymentBillRequest);

        String  response =  HttpXmlClient.post(salerWiseConfig.getQueryPaymentBillUrl(), queryPaymentBillParams);
        logger.info("账单查询返回结果:" + response);
        
        ResponseWrapper<List<PaymentBill>> paymentBill = sdmResponseParser.queryPaymentBillParse(response);
        List<PaymentBill> paymentBills = paymentBill.getData();
        if(null != paymentBills && paymentBills.size() > 0){
        	PaymentBill paymentBillPPPBill = paymentBills.get(0);
        	SwPayUnit swPayUnit = payUnitService.getSwPayUnitByPayUnitId(paymentBillPPPBill.getUnitId());
            //支持预付费类型（1是支持，2是不支持）
             if(null != swPayUnit){
             	if(1 == swPayUnit.getStatementType()){
             		paymentBillPPPBill.setPayModel(1);
             	}else{
             		paymentBillPPPBill.setPayModel(2);
             	}
             }else {
            	 paymentBillPPPBill.setPayModel(2);
    		}
        }
        paymentBill.setData(paymentBills);
        return paymentBill;
    }
    /**
     * 多账单查询接口
     * @author Administrator <br>
     * @Date 2016年11月14日<br>
     * @param paymentBillRequest
     * @return
     * @throws Exception
     */
    public ResponseWrapper<List<PaymentBill>> queryPaymentBillMore(PaymentBillRequest paymentBillRequest) throws Exception {
        if (paymentBillRequest == null)
            throw new  IllegalArgumentException("paymentBillRequest is null object");

        logger.info("账单查询请求参数:" + JSONObject.toJSONString(paymentBillRequest));
        Map<String, String> queryPaymentBillParams = sdmRequestBuilder.buildQueryPaymentBillMoreRequest(paymentBillRequest);

        String  response =  HttpXmlClient.post(salerWiseConfig.getQueryPaymentBillMoreUrl(), queryPaymentBillParams);
        logger.info("账单查询返回结果:" + response);

        return sdmResponseParser.queryPaymentBillParse(response);
    }

    @Override
    public ResponseWrapper<String> generateOrderNo(GenerateOrderNoRequest generateOrderNoRequest) {
        if (generateOrderNoRequest == null)
            throw new  IllegalArgumentException("generateOrderNoRequest is null object");
        logger.info("生成订单编号请求参数:" + JSONObject.toJSONString(generateOrderNoRequest));

        String userNumber = salerWiseConfig.getUserName();
        String outOrderNo = "IWEC" + userNumber + DateUtils.format(new Date(), "yyyyMMddHHmmss")
                + RandomUtils.generateRandomNo(4);
        logger.info("生成订单编号返回结果:" + outOrderNo);
        ResponseWrapper<String> result = new ResponseWrapper<String>();
        result.setData(outOrderNo);

        return result;
    }

}
