package com.tsh.broker.controller.ofpay;

import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.broker.utils.SdmRequestSignUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.request.*;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("all")
public class SdmOfPayControllerTest {

    @Test
    public void testQueryPayUnit() throws Exception {
        PayUnitRequest payUnitRequest = new PayUnitRequest();
        payUnitRequest.setBusinessId("100001");
        payUnitRequest.setPayType(2); //电费
        payUnitRequest.setProvince("安徽省");
        payUnitRequest.setCity("宿州市");
        payUnitRequest.setCounty("");
        payUnitRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signQueryPayUnit(payUnitRequest, "b429d7cefe48d55e");
        payUnitRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(payUnitRequest);
        //String  response =  HttpXmlClient.post("http://broker.tsh.com/sdm/ofpay/queryPayUnit.do", map);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/ofpay/queryPayUnit.do", map);
        System.out.println(response);
    }

    @Test
    public void testRecharge() throws Exception {
        GenerateOrderNoRequest generateOrderNoRequest = new GenerateOrderNoRequest();
        generateOrderNoRequest.setBusinessId("ZZFW20161029152334397419286");
        generateOrderNoRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, "c657cd4faa8258d2");
        generateOrderNoRequest.setSigned(signed);
        Map<String,String> generateOrderNoMap = ObjectMapUtils.toStringMap(generateOrderNoRequest);
        String  generateOrderNoResponse =  HttpXmlClient.post("http://testbroker.tsh.cn/sdm/ofpay/generateOrderNo.do", generateOrderNoMap);
        ResponseWrapper<String> responseWrapper = com.alibaba.fastjson.JSONObject.parseObject(generateOrderNoResponse,ResponseWrapper.class);

        RechargeRequest rechargeRequest = new RechargeRequest();
        rechargeRequest.setBusinessId("ZZFW20161029152334397419286");
        rechargeRequest.setInOrderNo("1123124");
        rechargeRequest.setOutOrderNo(responseWrapper.getData());
        rechargeRequest.setUnitId("v91463");
        rechargeRequest.setAccount("85010270268");
        rechargeRequest.setPayAmount(new BigDecimal("0.01"));
        rechargeRequest.setYearmonth("");
        rechargeRequest.setProductId("64121800");
        rechargeRequest.setPayType(2);
        rechargeRequest.setAccountType(1);
        rechargeRequest.setProvince("湖北省");
        rechargeRequest.setCity("恩施土家族苗族自治州");

        rechargeRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String rechargeSigned = SdmRequestSignUtils.signRecharge(rechargeRequest, "c657cd4faa8258d2");
        rechargeRequest.setSigned(rechargeSigned);
        Map<String,String> rechargeMap = ObjectMapUtils.toStringMap(rechargeRequest);

        String  rechargeResponse =  HttpXmlClient.post("http://testbroker.tsh.cn/sdm/ofpay/recharge.do", rechargeMap);
        System.out.println(rechargeResponse);
    }

    @Test
    public void testQueryRechargeResult() throws Exception {
        RechargeResultRequest rechargeResultRequest = new RechargeResultRequest();
        rechargeResultRequest.setBusinessId("ZZFW20161029152334397419286");
        rechargeResultRequest.setInOrderNo("1123124");
        rechargeResultRequest.setOutOrderNo("SP201610181622587168");
        rechargeResultRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signQueryRechargeResult(rechargeResultRequest, "b429d7cefe48d55e");
        rechargeResultRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(rechargeResultRequest);
        String  response =  HttpXmlClient.post("http://58.251.48.20:16001/sdm/ofpay/queryRechargeResult.do", map);
        System.out.println(response);
    }

    @Test
    public void testResultNotify() throws Exception {
        //1充值成功，0充值中，9充值失败，-1找不到此订单
        Map<String,String> paramsMap = new HashMap<String, String>();
        paramsMap.put("sporder_id","SP201610251600321204");
        paramsMap.put("ret_code","1");
        paramsMap.put("err_msg","成功");
        paramsMap.put("ordersuccesstime",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/ofpay/resultNotify.do", paramsMap);
        System.out.println(response);
    }

    @Test
    public void testQueryPaymentBill() throws Exception {
        PaymentBillRequest paymentBillRequest = new PaymentBillRequest();
        paymentBillRequest.setBusinessId("ZZFW20161029152334397419286");
        paymentBillRequest.setUnitId("v83503");
        paymentBillRequest.setUnitName("安徽省电力公司");
        paymentBillRequest.setAccount("5100332201");
        paymentBillRequest.setYearmonth("");
        paymentBillRequest.setPayType(2);
        paymentBillRequest.setAccountType(1);
        paymentBillRequest.setProvince("安徽");
        paymentBillRequest.setCity("合肥");
        paymentBillRequest.setProductId("64188601");
        paymentBillRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String paymentBillSigned = SdmRequestSignUtils.signPaymentBill(paymentBillRequest, "c657cd4faa8258d2");
        paymentBillRequest.setSigned(paymentBillSigned);

        Map<String,String> map = ObjectMapUtils.toStringMap(paymentBillRequest);
        String  response =  HttpXmlClient.post("http://testbroker.tsh.cn/sdm/ofpay/queryPaymentBill.do", map);
        System.out.println(response);
    }

    @Test
    public void testGenerateOrderNo() throws Exception {
        GenerateOrderNoRequest generateOrderNoRequest = new GenerateOrderNoRequest();
        generateOrderNoRequest.setBusinessId("100000");
        generateOrderNoRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, "b429d7cefe48d55e");
        generateOrderNoRequest.setSigned(signed);
        Map<String,String> generateOrderNoMap = ObjectMapUtils.toStringMap(generateOrderNoRequest);
        String  generateOrderNoResponse =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/ofpay/generateOrderNo.do", generateOrderNoMap);
        ResponseWrapper<String> responseWrapper = com.alibaba.fastjson.JSONObject.parseObject(generateOrderNoResponse,ResponseWrapper.class);
        System.out.println(responseWrapper);
    }
}