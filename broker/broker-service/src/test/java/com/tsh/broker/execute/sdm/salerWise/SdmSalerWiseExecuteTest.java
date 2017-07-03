package com.tsh.broker.execute.sdm.salerWise;

import com.alibaba.fastjson.JSONObject;
import com.tsh.broker.config.SalerWiseConfig;
import com.tsh.broker.execute.sdm.SdmExecute;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.SdmRequestSignUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.request.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-service.xml"})
public class SdmSalerWiseExecuteTest {

    @Resource(name = "sdmSalerWiseExecute")
    private SdmExecute sdmExecute;
    @Resource(name = "salerWiseConfig")
    private SalerWiseConfig salerWiseConfig;

    @Test
    public void testQueryPayUnit() throws Exception {
        PayUnitRequest payUnitRequest = new PayUnitRequest();
        payUnitRequest.setBusinessId("100000");
        payUnitRequest.setPayType(2); //电费
        payUnitRequest.setProvince("湖北省");
        payUnitRequest.setCity("潜江市");
        payUnitRequest.setCounty("");
        payUnitRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signQueryPayUnit(payUnitRequest, salerWiseConfig.getBusinessKey());
        payUnitRequest.setSigned(signed);
        ResponseWrapper<List<PayUnit>> responseWrapper = sdmExecute.queryPayUnit(payUnitRequest);

        Assert.assertEquals(0,responseWrapper.getStatus());
        System.out.println(JSONObject.toJSONString(responseWrapper));
    }

    @Test
    public void testRecharge() throws Exception {
        GenerateOrderNoRequest generateOrderNoRequest = new GenerateOrderNoRequest();
        generateOrderNoRequest.setBusinessId("100000");
        generateOrderNoRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, salerWiseConfig.getBusinessKey());
        generateOrderNoRequest.setSigned(signed);
        ResponseWrapper<String> responseWrapper = sdmExecute.generateOrderNo(generateOrderNoRequest);

        RechargeRequest rechargeRequest = new RechargeRequest();
        rechargeRequest.setInOrderNo("1123124");
        rechargeRequest.setOutOrderNo(responseWrapper.getData());
        rechargeRequest.setUnitId("2634");
        rechargeRequest.setAccount("4050034644");
        rechargeRequest.setPayAmount(new BigDecimal(1));
        rechargeRequest.setYearmonth("");
        rechargeRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String rechargeSigned = SdmRequestSignUtils.signRecharge(rechargeRequest, salerWiseConfig.getBusinessKey());
        rechargeRequest.setSigned(rechargeSigned);

        ResponseWrapper rechargeResponse = sdmExecute.recharge(rechargeRequest);
        Assert.assertEquals(0,rechargeResponse.getStatus());
        System.out.println(JSONObject.toJSONString(rechargeResponse));
    }

    @Test
    public void testQueryRechargeResult() throws Exception {
        RechargeResultRequest rechargeResultRequest = new RechargeResultRequest();
        rechargeResultRequest.setBusinessId("100000");
        rechargeResultRequest.setInOrderNo("1123124");
        rechargeResultRequest.setOutOrderNo("IWEC1500009201610111408463691");
        rechargeResultRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signQueryRechargeResult(rechargeResultRequest, salerWiseConfig.getBusinessKey());
        rechargeResultRequest.setSigned(signed);

        ResponseWrapper response = sdmExecute.queryRechargeResult(rechargeResultRequest);
        Assert.assertEquals(0,response.getStatus());
        System.out.println(JSONObject.toJSONString(response));
    }

    @Test
    public void testResultNotify() throws Exception {

    }

    @Test
    public void testQueryPaymentBill() throws Exception {
        PaymentBillRequest paymentBillRequest = new PaymentBillRequest();
        paymentBillRequest.setBusinessId("100000");
        paymentBillRequest.setUnitId("2127");
        paymentBillRequest.setAccount("85010270268");
        paymentBillRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String paymentBillSigned = SdmRequestSignUtils.signPaymentBill(paymentBillRequest, salerWiseConfig.getBusinessKey());
        paymentBillRequest.setSigned(paymentBillSigned);

        ResponseWrapper paymentBillResponse = sdmExecute.queryPaymentBill(paymentBillRequest);
        Assert.assertEquals(0,paymentBillResponse.getStatus());
        System.out.println(JSONObject.toJSONString(paymentBillResponse));
    }

    @Test
    public void testGenerateOrderNo() throws Exception {
        GenerateOrderNoRequest generateOrderNoRequest = new GenerateOrderNoRequest();
        generateOrderNoRequest.setBusinessId("100000");
        generateOrderNoRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, "123456");
        generateOrderNoRequest.setSigned(signed);

        ResponseWrapper response = sdmExecute.generateOrderNo(generateOrderNoRequest);
        Assert.assertEquals(0,response.getStatus());
        System.out.println(JSONObject.toJSONString(response));
    }
}