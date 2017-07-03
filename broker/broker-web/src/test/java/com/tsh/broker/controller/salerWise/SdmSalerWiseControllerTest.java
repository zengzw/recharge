package com.tsh.broker.controller.salerWise;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.broker.utils.SdmRequestSignUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.request.GenerateOrderNoRequest;
import com.tsh.broker.vo.sdm.request.PayUnitRequest;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;
import com.tsh.broker.vo.sdm.request.RechargeRequest;
import com.tsh.broker.vo.sdm.request.RechargeResultRequest;
/**
 * 易赛水电煤接口测试代码
 * @version 1.0.0 2016年11月9日<br>
 * @see 
 * @since JDK 1.7.0
 */
@SuppressWarnings("all")
public class SdmSalerWiseControllerTest {
	/**
	 * 根据省份和市查询充值缴费收费单位
	 * @author Administrator <br>
	 * @Date 2016年11月11日<br>
	 * @throws Exception
	 */
    @Test
    public void testQueryPayUnit() throws Exception {
        PayUnitRequest payUnitRequest = new PayUnitRequest();
        payUnitRequest.setBusinessId("100000");
        payUnitRequest.setPayType(2); //电费
        payUnitRequest.setProvince("湖北省");
        payUnitRequest.setCity("潜江市");
        payUnitRequest.setCounty("");
        payUnitRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signQueryPayUnit(payUnitRequest, "b429d7cefe48d55e");
        payUnitRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(payUnitRequest);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/queryPayUnit.do",map);
        System.out.println(response);
    }
    /**
     * 生成订单并调用充值接口
     * 接口 正式 地址：http://lifeapi.salerwise.com/IWEC/IRechargeList_WEC
	 * 接口测试地址：http://lifeapi.salerwise.com/IWEC/IRechargeList_WEC_Test
     * @author Administrator <br>
     * @Date 2016年11月11日<br>
     * @throws Exception
     */
    @Test
    public void testRecharge() throws Exception {
        GenerateOrderNoRequest generateOrderNoRequest = new GenerateOrderNoRequest();
        generateOrderNoRequest.setBusinessId("100000");
        generateOrderNoRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, "b429d7cefe48d55e");
        generateOrderNoRequest.setSigned(signed);
        Map<String,String> generateOrderNoMap = ObjectMapUtils.toStringMap(generateOrderNoRequest);
        //String  generateOrderNoResponse =  HttpXmlClient.post("http://broker.tsh.com/sdm/salerwise/generateOrderNo.do", generateOrderNoMap);
        String  generateOrderNoResponse =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/generateOrderNo.do", generateOrderNoMap);
        ResponseWrapper<String> responseWrapper = com.alibaba.fastjson.JSONObject.parseObject(generateOrderNoResponse,ResponseWrapper.class);

        RechargeRequest rechargeRequest = new RechargeRequest();
        rechargeRequest.setBusinessId("100000");
        rechargeRequest.setInOrderNo("RRTTTT23232312");	//内部订单号
        rechargeRequest.setOutOrderNo(responseWrapper.getData());	//外部订单号
        rechargeRequest.setUnitId("2127");	//缴费单位编号
        rechargeRequest.setAccount("85010270268");	//缴费账户
        rechargeRequest.setPayAmount(new BigDecimal(0.01));	//缴费金额(单位分)
        rechargeRequest.setYearmonth("");
        rechargeRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String rechargeSigned = SdmRequestSignUtils.signRecharge(rechargeRequest, "b429d7cefe48d55e");
        rechargeRequest.setSigned(rechargeSigned);
        Map<String,String> rechargeMap = ObjectMapUtils.toStringMap(rechargeRequest);

        //String  rechargeResponse =  HttpXmlClient.post("http://broker.tsh.com/sdm/salerwise/recharge.do", rechargeMap);
        String  rechargeResponse =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/recharge.do", rechargeMap);
        System.out.println(rechargeResponse);
    }
    /**
     * 充值结果查询
     * 接口正式 地址：http://lifeapi.salerwise.com/IWEC/IRechargeResult
	 * 接口测试 地址：http://lifeapi.salerwise.com/IWEC/IRechargeResult_TEST
     * @author Administrator <br>
     * @Date 2016年11月11日<br>
     * @throws Exception
     */
    @Test
    public void testQueryRechargeResult() throws Exception {
        RechargeResultRequest rechargeResultRequest = new RechargeResultRequest();
        rechargeResultRequest.setBusinessId("100000");
        rechargeResultRequest.setInOrderNo("1234564");
        rechargeResultRequest.setOutOrderNo("IWEC7001021201611141525467395");
        rechargeResultRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signQueryRechargeResult(rechargeResultRequest, "b429d7cefe48d55e");
        rechargeResultRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(rechargeResultRequest);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/queryRechargeResult.do", map);
        System.out.println(response);
    }
    /**
     * 异步回调
     * @author Administrator <br>
     * @Date 2016年11月11日<br>
     * @throws Exception
     */
    @Test
    public void testResultNotify() throws Exception {

    }
    /**
     * 查询缴费账单接口
     * 接口 正式 地址：http://lifeapi.salerwise.com/IWEC/BillsQuery
	 * 接口 测试 地址：http://lifeapi.salerwise.com/IWEC/BillsQuery_TEST
     * @author Administrator <br>
     * @Date 2016年11月11日<br>
     * @throws Exception
     */
    @Test
    public void testQueryPaymentBill() throws Exception {
        PaymentBillRequest paymentBillRequest = new PaymentBillRequest();
        /*paymentBillRequest.setBusinessId("100000");
        paymentBillRequest.setUnitId("2634");
        paymentBillRequest.setAccount("4050034644");*/
        
        paymentBillRequest.setBusinessId("ZZFW20161116203055634051294");
        paymentBillRequest.setUnitId("2127");
        paymentBillRequest.setAccount("85010270268");
        
        paymentBillRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String paymentBillSigned = SdmRequestSignUtils.signPaymentBill(paymentBillRequest, "a23d55a3af741b7f");
        paymentBillRequest.setSigned(paymentBillSigned);

        Map<String,String> map = ObjectMapUtils.toStringMap(paymentBillRequest);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/queryPaymentBill.do", map);
        System.out.println(response);
    }
    /**
     * 多账单查询 http://lifeapi.salerwise.com/MultiBillsQuery_WEC
     * @author Administrator <br>
     * @Date 2016年11月14日<br>
     * @throws Exception
     */
    @Test
    public void testQueryPaymentBillMore() throws Exception {
        PaymentBillRequest paymentBillRequest = new PaymentBillRequest();
        paymentBillRequest.setBusinessId("100000");
        paymentBillRequest.setUnitId("2127");
        paymentBillRequest.setAccount("8400019734");
        
        paymentBillRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String paymentBillSigned = SdmRequestSignUtils.signPaymentBill(paymentBillRequest, "b429d7cefe48d55e");
        paymentBillRequest.setSigned(paymentBillSigned);

        Map<String,String> map = ObjectMapUtils.toStringMap(paymentBillRequest);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/queryPaymentBillMore.do", map);
        System.out.println(response);
    }
    /**
     * 生成订单
     * @author Administrator <br>
     * @Date 2016年11月11日<br>
     * @throws Exception
     */
    @Test
    public void testGenerateOrderNo() throws Exception {
        GenerateOrderNoRequest generateOrderNoRequest = new GenerateOrderNoRequest();
        generateOrderNoRequest.setBusinessId("100000");
        generateOrderNoRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = SdmRequestSignUtils.signGenerateOrderNo(generateOrderNoRequest, "b429d7cefe48d55e");
        generateOrderNoRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(generateOrderNoRequest);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8210/sdm/salerwise/generateOrderNo.do", map);
        System.out.println(response);
    }
}