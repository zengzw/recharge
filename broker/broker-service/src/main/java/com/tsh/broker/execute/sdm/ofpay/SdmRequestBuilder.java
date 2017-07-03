package com.tsh.broker.execute.sdm.ofpay;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.tsh.broker.config.OfPayConfig;
import com.tsh.broker.convertor.PayResultConvetor;
import com.tsh.broker.enumType.BizzTypeEnum;
import com.tsh.broker.enumType.OfpayEnum;
import com.tsh.broker.po.AreaMappingInfo;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.Md5Digest;
import com.tsh.broker.utils.SignUtils;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.request.PayUnitRequest;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;

/**
 * SdmRequestBuilder
 *
 * @author dengjd
 * @date 2016/10/15
 */
@SuppressWarnings("all")
public class SdmRequestBuilder {

    private OfPayConfig ofPayConfig;

    public SdmRequestBuilder(OfPayConfig ofPayConfig){
        this.ofPayConfig = ofPayConfig;
    }

    public Map<String, String> buildQueryPayUnitRequest(PayUnitRequest payUnitRequest, AreaMappingInfo areaMappingInfo) throws Exception {
        Map<String,String> requestParams = new LinkedHashMap<String,String>();
        //用户编号
        requestParams.put("userid", ofPayConfig.getUserName());
        //密码
        requestParams.put("userpws", Md5Digest.encryptMD5(ofPayConfig.getSignKey()).toLowerCase());
        requestParams.put("provId", areaMappingInfo.getTargetProvinceNo());
        requestParams.put("cityId", areaMappingInfo.getTargetCityNo());
        requestParams.put("type", OfpayEnum.PayProjectEnum.DEFAULT.valueOf(payUnitRequest.getPayType()).getName());
        requestParams.put("version", "6.0");

        return requestParams;
    }

    public Map<String, String> buildPayModeRequest(PayUnit payUnit, AreaMappingInfo areaMappingInfo) throws Exception {
        Map<String,String> requestParams = new LinkedHashMap<String,String>();
        //用户编号
        requestParams.put("userid", ofPayConfig.getUserName());
        //密码
        requestParams.put("userpws", Md5Digest.encryptMD5(ofPayConfig.getSignKey()).toLowerCase());
        requestParams.put("provId", areaMappingInfo.getTargetProvinceNo());
        requestParams.put("cityId", areaMappingInfo.getTargetCountyNo());
        requestParams.put("type", OfpayEnum.RechargeTypeEnum.DEFAULT.valueOf(payUnit.getPayType()).getName());
        requestParams.put("chargeCompanyCode", payUnit.getUnitId());
        requestParams.put("version", "6.0");

        return requestParams;
    }


    public Map<String, String> buildQueryClassIdRequest(PayUnit payUnit, AreaMappingInfo areaMappingInfo) throws Exception {
        Map<String,String> requestParams = new LinkedHashMap<String,String>();
        //用户编号
        requestParams.put("userid", ofPayConfig.getUserName());
        //密码
        requestParams.put("userpws", Md5Digest.encryptMD5(ofPayConfig.getSignKey()).toLowerCase());
        requestParams.put("provId", areaMappingInfo.getTargetProvinceNo());
        requestParams.put("cityId", areaMappingInfo.getTargetCityNo());
        requestParams.put("type", OfpayEnum.PayProjectEnum.DEFAULT.valueOf(payUnit.getPayType()).getName());
        requestParams.put("chargeCompanyCode", payUnit.getUnitId());
        requestParams.put("payModeId", OfpayEnum.AccountTypeEnum.DEFAULT.valueOf(payUnit.getAccountType()).getName() );
        requestParams.put("version", "6.0");

        return requestParams;
    }

    public Map<String, String> buildQueryPaymentBillRequest(PaymentBillRequest paymentBillRequest) throws Exception {
        Map<String,String> requestParams = new LinkedHashMap<String,String>();
        //用户编号
        requestParams.put("userid", ofPayConfig.getUserName());
        //密码
        requestParams.put("userpws", Md5Digest.encryptMD5(ofPayConfig.getSignKey()).toLowerCase());

        requestParams.put("provName", URLEncoder.encode(paymentBillRequest.getProvince(), "GBK"));
        requestParams.put("cityName", URLEncoder.encode(paymentBillRequest.getCity(), "GBK"));
        requestParams.put("type", OfpayEnum.ChargeTypeEnum.DEFAULT.valueOf(paymentBillRequest.getPayType()).getName());
        requestParams.put("chargeCompanyCode", paymentBillRequest.getUnitId());
        requestParams.put("chargeCompanyName", URLEncoder.encode(paymentBillRequest.getUnitName(), "GBK"));
        requestParams.put("account", paymentBillRequest.getAccount());
        requestParams.put("cardId", paymentBillRequest.getProductId());
        requestParams.put("payModeId", OfpayEnum.AccountTypeEnum.DEFAULT.valueOf(paymentBillRequest.getAccountType()).getName());
        requestParams.put("version", "6.0");

        return requestParams;
    }

    public Map<String, String> buildRechargeRequest(RechargeRequest rechargeRequest, AreaMappingInfo areaMappingInfo) throws Exception {
        LinkedHashMap<String,String> requestParams = new LinkedHashMap<String,String>();
        //用户编号
        requestParams.put("userid", ofPayConfig.getUserName());
        requestParams.put("userpws", Md5Digest.encryptMD5(ofPayConfig.getSignKey()).toLowerCase());
        requestParams.put("cardId", rechargeRequest.getProductId());
        requestParams.put("cardnum", "1");
        requestParams.put("sporderId",rechargeRequest.getOutOrderNo() );
        requestParams.put("provId", areaMappingInfo.getTargetProvinceNo());
        requestParams.put("cityId", areaMappingInfo.getTargetCityNo());
        requestParams.put("type", OfpayEnum.ChargeTypeEnum.DEFAULT.valueOf(rechargeRequest.getPayType()).getName());
        requestParams.put("chargeCompanyCode", rechargeRequest.getUnitId());
        requestParams.put("account", rechargeRequest.getAccount());
        requestParams.put("md5_str", SignUtils.bizzSign(BizzTypeEnum.OF_PAY, requestParams,ofPayConfig.getRechargeSignKey()).toUpperCase());
        requestParams.put("ret_url",ofPayConfig.getCallbackUrl());
        //转换分金额单位
        requestParams.put("actPrice",String.valueOf(rechargeRequest.getPayAmount().setScale(4, BigDecimal.ROUND_HALF_UP)));
        requestParams.put("payModeId", OfpayEnum.AccountTypeEnum.DEFAULT.valueOf(rechargeRequest.getAccountType()).getName());
        if(StringUtils.isNotBlank(rechargeRequest.getYearmonth()))
            requestParams.put("payMentDay", rechargeRequest.getYearmonth());
        if(StringUtils.isNotBlank(rechargeRequest.getExtContent())){
            Map<String,String> extContentMap = JSONObject.parseObject(rechargeRequest.getExtContent(), Map.class);
            if(extContentMap.containsKey("contractNo")){
                requestParams.put("contractNo",extContentMap.get("contractNo"));
            }
            if(extContentMap.containsKey("param1")){
                requestParams.put("param1",extContentMap.get("param1"));
            }
        }
        requestParams.put("version", "6.0");

        return requestParams;
    }

    public Map<String, String> buildResultNotifyRequest(ResultNotifyRequest resultNotifyRequest) {
        LinkedHashMap<String,String> resultNotifyParams = new LinkedHashMap<String,String>();
        resultNotifyParams.put("businessId", ofPayConfig.getBusinessNo());
        resultNotifyParams.put("inOrderNo", resultNotifyRequest.getInOrderNo());
        resultNotifyParams.put("outOrderNo", resultNotifyRequest.getOutOrderNo());
        resultNotifyParams.put("payResult",String.valueOf(PayResultConvetor.convertPayResult(resultNotifyRequest.getPayResult())));
        resultNotifyParams.put("timestamp", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        resultNotifyParams.put("signed",
                SignUtils.bizzSign(BizzTypeEnum.DEFAULT, resultNotifyParams, ofPayConfig.getBusinessKey()));

        return resultNotifyParams;
    }

    public Map<String, String> buildQueryRechargeResultRequest(RechargeResultRequest rechargeResultRequest) {
        Map<String,String> requestParams = new LinkedHashMap<String,String>();
        //用户编号
        requestParams.put("userid", ofPayConfig.getUserName());
        //订单编号
        requestParams.put("spbillid", rechargeResultRequest.getOutOrderNo());

        return requestParams;
    }
}
