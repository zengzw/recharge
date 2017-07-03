package com.tsh.broker.utils;

import com.tsh.broker.enumType.BizzTypeEnum;
import com.tsh.broker.vo.sdm.request.*;

import java.util.LinkedHashMap;

/**
 * SdmRequestSignUtils
 *
 * @author dengjd
 * @date 2016/10/10
 */
public class SdmRequestSignUtils {

    public static  String signQueryPayUnit(PayUnitRequest payUnitRequest,String businessKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", payUnitRequest.getBusinessId());
        queryPayParams.put("payType", payUnitRequest.getProvince());
        queryPayParams.put("city",payUnitRequest.getCity());
        queryPayParams.put("county",payUnitRequest.getCounty());
        queryPayParams.put("timestamp", payUnitRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, businessKey);
    }

    public static String signRecharge(RechargeRequest rechargeRequest,String businessKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", rechargeRequest.getBusinessId());
        queryPayParams.put("inOrderNo", rechargeRequest.getInOrderNo());
        queryPayParams.put("outOrderNo",rechargeRequest.getOutOrderNo());
        queryPayParams.put("unitId",rechargeRequest.getUnitId());
        queryPayParams.put("account",rechargeRequest.getAccount());
        queryPayParams.put("payAmount",String.valueOf(rechargeRequest.getPayAmount()));
        queryPayParams.put("yearmonth",String.valueOf(rechargeRequest.getYearmonth()));
        queryPayParams.put("timestamp", rechargeRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, businessKey);
    }

    public static String signQueryRechargeResult(RechargeResultRequest rechargeResultRequest,String businessKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", rechargeResultRequest.getBusinessId());
        queryPayParams.put("inOrderNo", rechargeResultRequest.getInOrderNo());
        queryPayParams.put("outOrderNo",rechargeResultRequest.getOutOrderNo());
        queryPayParams.put("timestamp", rechargeResultRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, businessKey);
    }

    public static String signPaymentBill(PaymentBillRequest paymentBillRequest,String businessKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", paymentBillRequest.getBusinessId());
        queryPayParams.put("unitId", paymentBillRequest.getUnitId());
        queryPayParams.put("account",paymentBillRequest.getAccount());
        queryPayParams.put("yearmonth",paymentBillRequest.getYearmonth());
        queryPayParams.put("timestamp", paymentBillRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, businessKey);
    }


    public static String signGenerateOrderNo(GenerateOrderNoRequest generateOrderNoRequest,String businessKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", generateOrderNoRequest.getBusinessId());
        queryPayParams.put("timestamp", generateOrderNoRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, businessKey);
    }

    public static String signResultNotify(ResultNotifyRequest resultNotifyRequest,String signKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", resultNotifyRequest.getBusinessId());
        queryPayParams.put("InOrderNumb", resultNotifyRequest.getOutOrderNo());
        queryPayParams.put("OutOrderNumber", resultNotifyRequest.getInOrderNo());
        queryPayParams.put("PayResult", String.valueOf(resultNotifyRequest.getPayResult()));

        return SignUtils.bizzSign(BizzTypeEnum.SALER_WISE, queryPayParams, signKey);
    }

    public static String signResultNotifyRequest(ResultNotifyRequest resultNotifyRequest,String signKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", resultNotifyRequest.getBusinessId());
        queryPayParams.put("inOrderNumb", resultNotifyRequest.getOutOrderNo());
        queryPayParams.put("outOrderNumber", resultNotifyRequest.getInOrderNo());
        queryPayParams.put("payResult", String.valueOf(resultNotifyRequest.getPayResult()));

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, signKey);
    }

}
