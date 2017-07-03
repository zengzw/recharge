package com.tsh.broker.execute.sdm.salerWise;

import com.tsh.broker.config.SalerWiseConfig;
import com.tsh.broker.convertor.PayResultConvetor;
import com.tsh.broker.enumType.BizzTypeEnum;
import com.tsh.broker.utils.*;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SdmRequestBuilder
 *
 * @author dengjd
 * @date 2016/10/9
 */
public class SdmRequestBuilder {

    private SalerWiseConfig salerWiseConfig;

    public SdmRequestBuilder(SalerWiseConfig salerWiseConfig){
        this.salerWiseConfig = salerWiseConfig;
    }

    public  Map<String,String> buildRechargeRequest(RechargeRequest rechargeRequest) throws Exception {

        LinkedHashMap<String,String> rechargeParams = new LinkedHashMap<String,String>();
        //用户编号
        rechargeParams.put("usernumber",salerWiseConfig.getUserName());
        Date curDate = new Date();
        rechargeParams.put("inordernumber",rechargeRequest.getOutOrderNo());
        //外部订单号
        rechargeParams.put("outordernumber", EmptyStringUtils.emptyToNone(rechargeRequest.getInOrderNo()));
        //产品编号
        rechargeParams.put("proId", rechargeRequest.getUnitId());
        //缴费号
        rechargeParams.put("account",rechargeRequest.getAccount());
        //缴费金额
        rechargeParams.put("paymoney",String.valueOf(rechargeRequest.getPayAmount().multiply(new BigDecimal(100)).longValue()));
        //账单月份,时间格式yyyyMM
        rechargeParams.put("yearmonth", rechargeRequest.getYearmonth());
        //时间戳，时间格式yyyy-MM-dd HH:mm:ss
        rechargeParams.put("starttime", DateUtils.format(curDate, "yyyy-MM-dd HH:mm:ss"));
        //timeout
        rechargeParams.put("timeout",String.valueOf(salerWiseConfig.getRechargeTimeout()));
        rechargeParams.put("attr1","");
        rechargeParams.put("attr2","");
        rechargeParams.put("recordkey",
                SignUtils.bizzSign(BizzTypeEnum.SALER_WISE, rechargeParams, salerWiseConfig.getSignKey()));

        return rechargeParams;
    }

    public Map<String,String> buildQueryRechargeResultRequest(RechargeResultRequest rechargeResultRequest) throws Exception {
        LinkedHashMap<String,String> queryRechargeParams = new LinkedHashMap<String,String>();
        queryRechargeParams.put("UserNumber",salerWiseConfig.getUserName());
        queryRechargeParams.put("InOrderNumber",rechargeResultRequest.getOutOrderNo());
        queryRechargeParams.put("OutOrderNumber",EmptyStringUtils.emptyToNone(rechargeResultRequest.getInOrderNo()));
        queryRechargeParams.put("QueryType","WEC");
        queryRechargeParams.put("recordkey",
            SignUtils.bizzSign(BizzTypeEnum.SALER_WISE, queryRechargeParams, salerWiseConfig.getSignKey()));

        return queryRechargeParams;
    }

    public Map<String,String> buildResultNotifyRequest(ResultNotifyRequest rechargeResultRequest) throws Exception {
        LinkedHashMap<String,String> resultNotifyParams = new LinkedHashMap<String,String>();
        resultNotifyParams.put("businessId", salerWiseConfig.getBusinessNo());
        resultNotifyParams.put("outOrderNo", rechargeResultRequest.getOutOrderNo());
        resultNotifyParams.put("inOrderNo", rechargeResultRequest.getInOrderNo());
        resultNotifyParams.put("payResult",String.valueOf(PayResultConvetor.convertNotifyStatus(rechargeResultRequest.getPayResult())));
        resultNotifyParams.put("timestamp", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        resultNotifyParams.put("signed",
                SignUtils.bizzSign(BizzTypeEnum.DEFAULT, resultNotifyParams, salerWiseConfig.getBusinessKey()));

        return resultNotifyParams;
    }

    public Map<String,String> buildQueryPaymentBillRequest(PaymentBillRequest paymentBillRequest) throws Exception {
        LinkedHashMap<String,String> billsQueryParams = new LinkedHashMap<String,String>();
        Date curDate = new Date();
        //用户编号
        billsQueryParams.put("usernumber",salerWiseConfig.getUserName());
        //产品编号
        billsQueryParams.put("proId",paymentBillRequest.getUnitId());
        //缴费号
        billsQueryParams.put("account",paymentBillRequest.getAccount());
        //账单月份,时间格式yyyyMM
        billsQueryParams.put("yearmonth", EmptyStringUtils.emptyToNone(paymentBillRequest.getYearmonth()));

        billsQueryParams.put("timestamp",DateUtils.format(curDate,"yyyy-MM-dd HH:mm:ss"));
        billsQueryParams.put("queryattr1","");
        billsQueryParams.put("queryattr2","");

        billsQueryParams.put("recordkey",
                SignUtils.bizzSign(BizzTypeEnum.SALER_WISE, billsQueryParams, salerWiseConfig.getSignKey()));

        return billsQueryParams;
    }
    
    public Map<String,String> buildQueryPaymentBillMoreRequest(PaymentBillRequest paymentBillRequest) throws Exception {
        LinkedHashMap<String,String> billsQueryParams = new LinkedHashMap<String,String>();
        Date curDate = new Date();
        //用户编号
        billsQueryParams.put("UserNumber",salerWiseConfig.getUserName());
        //产品编号
        billsQueryParams.put("ProId",paymentBillRequest.getUnitId());  
        //缴费号
        billsQueryParams.put("Account",paymentBillRequest.getAccount());
        //账单月份,时间格式yyyyMM
        billsQueryParams.put("YearMonth",null);

        billsQueryParams.put("TimeStamp",DateUtils.format(curDate,"yyyy-MM-dd HH:mm:ss"));
        billsQueryParams.put("queryattr1","");
        billsQueryParams.put("queryattr2","");
        
        billsQueryParams.put("RecordKey",SignUtils.bizzSign(BizzTypeEnum.SALER_WISE, billsQueryParams, salerWiseConfig.getSignKey()));

        return billsQueryParams;
    }
    
}
