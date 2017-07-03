package com.tsh.vas.service.charge;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.broker.enumType.PayResultType;
import com.tsh.broker.vo.sdm.PaymentBill;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.sdm.httplog.MongoHttpLogTask;
import com.tsh.vas.sdm.service.charge.PayChargeService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.utils.WorkerGroup;
import com.tsh.vas.vo.charge.BillInfoVo;
import com.tsh.vas.vo.charge.ChargeInfoVo;
import com.tsh.vas.vo.charge.ChargeOrgVo;
import com.tsh.vas.vo.charge.RechargeParams;

/**
 * PayChargeService Tester.
 *
 * @author Iritchie.ren
 * @version 1.0
 * @since <pre>十月 18, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class PayChargeServiceTest extends BaseCaseTest {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private PayChargeService payChargeService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: getOrganizationInfo(Result result, OrganizationVo organizationVo)
     */
    @Test
    public void testGetOrganizationInfo() throws Exception { 
        Result result = getResult ();
        UserInfo userInfo = result.getUserInfo ();
        //        userInfo.setBizId (18951L);
        userInfo.setBizId (81L);
        String businessCode = "DJDF";
        result = this.payChargeService.getOrganizationInfo (result, businessCode);
        System.err.println ("{businessCode:" + businessCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: getBillInfo(Result result, String chargeOrgCode, String supplierCode)
     */
    @Test
    public void testGetBillInfo() throws Exception {

        Result result = getResult ();
        RechargeParams<BillInfoVo> rechargeParams = new RechargeParams<BillInfoVo> ();
        rechargeParams.setServerAddr ("http://broke.tsh.com/sdm/ofpay/");
        rechargeParams.setSupplierCode ("100001");
        rechargeParams.setSupplierToken ("b429d7cefe48d55e");
        BillInfoVo billInfoVo = new BillInfoVo ();
        billInfoVo.setChargeOrgCode ("v85002");
        billInfoVo.setChargeOrgName ("国网浙江省电力公司");
        billInfoVo.setProvince ("浙江");
        billInfoVo.setCity ("杭州");
        billInfoVo.setProductId ("64156301");
        billInfoVo.setAccount ("3801015289");
        billInfoVo.setPayType ("2");
        billInfoVo.setAccountType ("1");
        rechargeParams.setData (billInfoVo);
        result = this.payChargeService.getBillInfo (result, rechargeParams);
        System.err.println (JSONObject.toJSONString (billInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: addChargeInfo(Result result, ChargeInfoVo chargeInfoVo)
     */
    @Test
    public void testPayChargeInfo() throws Exception {
        //网点代付
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        userInfo.setUserId (163L);
        userInfo.setBizId (51L);
        result.setUserInfo (userInfo);
        ChargeInfoVo chargeInfoVo = new ChargeInfoVo ();
        //欧飞100001
        chargeInfoVo.setSupplierCode ("ZZFW20161116203055634051294");
        chargeInfoVo.setBusinessCode ("DJDF");
        chargeInfoVo.setChargeOrgCode ("0000112");
        chargeInfoVo.setChargeOrgName ("湖南省常德市电力局");
        chargeInfoVo.setMobile ("15811824071");
        chargeInfoVo.setOriginalAmount (new BigDecimal (50));
        chargeInfoVo.setRealAmount (new BigDecimal (60));
        chargeInfoVo.setRechargeUserCode ("123456");
        chargeInfoVo.setBizType (RoleType.SHOP.getCode ());
        chargeInfoVo.setRechargeUserName ("zengzw");
        chargeInfoVo.setPayPassword ("123456");
        chargeInfoVo.setSupplierToken ("d3369bf47c81af33");
        chargeInfoVo.setServerAddr ("http://broker.tsh.com/sdm/ofpay/");
        chargeInfoVo.setBillYearMonth ("201602");
        chargeInfoVo.setRechargeUserType (1);
        chargeInfoVo.setProductId ("1220");
        result = this.payChargeService.payChargeInfo (result, chargeInfoVo);
        System.err.println (JSONObject.toJSONString (chargeInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: addChargeInfo(Result result, ChargeInfoVo chargeInfoVo)
     */
    @Test
    public void testPayChargeInfo2() throws Exception {
        //会员支付
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        userInfo.setUserId (1101L);
        userInfo.setBizId (121L);
        result.setUserInfo (userInfo);
        ChargeInfoVo chargeInfoVo = new ChargeInfoVo ();
        chargeInfoVo.setSupplierCode ("100000");
        chargeInfoVo.setBusinessCode ("DJDF");
        chargeInfoVo.setChargeOrgCode ("0000112");
        chargeInfoVo.setChargeOrgName ("湖南省常德市电力局");
        chargeInfoVo.setMobile ("13510214190");
        chargeInfoVo.setOriginalAmount (new BigDecimal (50));
        chargeInfoVo.setRealAmount (new BigDecimal (80));
        chargeInfoVo.setRechargeUserCode ("123456");
        chargeInfoVo.setBizType (RoleType.MEMBER.getCode ());
        chargeInfoVo.setRechargeUserName ("任欢");
        chargeInfoVo.setPayPassword ("123456");
        result = this.payChargeService.payChargeInfo (result, chargeInfoVo);
        System.err.println (JSONObject.toJSONString (chargeInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: getBillInfo(Result result, String chargeOrgCode, String supplierCode)
     */
    @Test
    public void testCompositePayChargeInfo() throws Exception {
        //网点登录用户账号信息
        Long bizId = 81L;
        //业务类型
        String businessCode = "DJDF";
        //缴费用户账号,湖北省恩施市巴东县
        String account = "85010270268";
        //账单月份YYYYMM
        String billMonth = "";
        //用户实付
        BigDecimal realAmount = new BigDecimal (0);
        //付费用户手机号
        String mobile = "18676823505";
        //付费用户支付密码
        String password = "123456";

        Result result = getResult ();
        UserInfo userInfo = result.getUserInfo ();
        userInfo.setBizId (bizId);
        userInfo.setUserId (648L);
        result.setUserInfo (userInfo);

        result = this.payChargeService.getOrganizationInfo (result, businessCode);
        System.err.println ("{businessCode:" + businessCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
        ChargeOrgVo chargeOrgVo = result.getData ();
        ChargeOrgVo.UnitInfo unitInfo = chargeOrgVo.getUnitInfos ().get (0);

        //#######################

        RechargeParams<BillInfoVo> rechargeParams = new RechargeParams<BillInfoVo> ();
        rechargeParams.setServerAddr (chargeOrgVo.getServerAddr ());
        rechargeParams.setSupplierCode (chargeOrgVo.getSupplierCode ());
        rechargeParams.setSupplierToken (chargeOrgVo.getSupplierToken ());
        BillInfoVo billInfoVo = new BillInfoVo ();
        billInfoVo.setChargeOrgCode (unitInfo.getChargeOrgCode ());
        billInfoVo.setChargeOrgName (unitInfo.getChargeOrgName ());
        billInfoVo.setProvince (unitInfo.getProvince ());
        billInfoVo.setCity (unitInfo.getCity ());
        billInfoVo.setProductId (unitInfo.getProductId ());
        billInfoVo.setAccount (account);
        billInfoVo.setPayType (String.valueOf (unitInfo.getPayType ()));
        billInfoVo.setAccountType (String.valueOf (unitInfo.getAccountType ()));
        rechargeParams.setData (billInfoVo);
        result.setData (null);
        result = this.payChargeService.getBillInfo (result, rechargeParams);
        System.err.println (JSONObject.toJSONString (billInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
        List<PaymentBill> paymentBills = result.getData ();
        PaymentBill paymentBill = paymentBills.get (0);

        //####################
        //网点代付
        ChargeInfoVo chargeInfoVo = new ChargeInfoVo ();
        //欧飞100001
        chargeInfoVo.setSupplierCode (chargeOrgVo.getSupplierCode ());
        chargeInfoVo.setBusinessCode (businessCode);
        chargeInfoVo.setChargeOrgCode (unitInfo.getChargeOrgCode ());
        chargeInfoVo.setChargeOrgName (unitInfo.getChargeOrgName ());
        chargeInfoVo.setMobile (mobile);
        chargeInfoVo.setOriginalAmount (paymentBill.getBalance ());
        chargeInfoVo.setRealAmount (realAmount);
        chargeInfoVo.setRechargeUserCode (paymentBill.getAccount ());
        chargeInfoVo.setRechargeUserName (paymentBill.getUsername ());
        chargeInfoVo.setPayPassword (password);
        chargeInfoVo.setSupplierToken (chargeOrgVo.getSupplierToken ());
        chargeInfoVo.setServerAddr (chargeOrgVo.getServerAddr ());
        chargeInfoVo.setBillYearMonth (billMonth);
        chargeInfoVo.setBizType (RoleType.SHOP.getCode ());
        chargeInfoVo.setRechargeUserType (unitInfo.getAccountType ());
        chargeInfoVo.setProductId (unitInfo.getProductId ());
        result.setData (null);
        result = this.payChargeService.payChargeInfo (result, chargeInfoVo);
        System.err.println (JSONObject.toJSONString (chargeInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: payBack(Result result, ChargePayBackVo chargePayBackVo)
     */
    @Test
    public void testPayBack() throws Exception {
        String chargeCode = "DJDF20161119163547857810793";
        Result result = getResult ();
        MQBizOrderPay mqBizOrderPay = new MQBizOrderPay ();
        mqBizOrderPay.setBizOrderNo (chargeCode);
        mqBizOrderPay.setTotalMoney (60L);
        result = this.payChargeService.payBack (result, mqBizOrderPay);
        System.err.println (JSONObject.toJSONString (mqBizOrderPay));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: rechargeBack(Result result, RechargeBackVo rechargeBackVo)
     */
    @Test
    public void testRechargeBack() throws Exception {
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        userInfo.setUserId (1101L);
        userInfo.setBizId (121L);
        result.setUserInfo (userInfo);

        ResultNotifyRequest resultNotifyRequest = new ResultNotifyRequest ();
        resultNotifyRequest.setOutOrderNo ("SP201611191635470189");
//        resultNotifyRequest.setPayResult (PayResultType.CHARGING_SUCCESS.getCode ());      //订单结果
        resultNotifyRequest.setPayResult (PayResultType.CHARGING_FAIL.getCode ());      //订单结果
        resultNotifyRequest.setResultMsg ("测试成功");    //通知结果描述信息

        result = this.payChargeService.rechargeBack (result, resultNotifyRequest);
        System.err.println (JSONObject.toJSONString (resultNotifyRequest));
        System.err.println (result.getData ());

        if(result.getStatus() != 200){
            //确认扣款、分润
            result = this.payChargeService.confirmSettle(result, resultNotifyRequest.getOutOrderNo (), false);
            //退款，收回分润
//            this.payChargeService.refundCharge (result,resultNotifyRequest.getOutOrderNo ());
        }else if(result.getStatus() == 200){
            //确认扣款、分润
            result = this.payChargeService.confirmSettle(result, resultNotifyRequest.getOutOrderNo (), true);
        }
    }

    /**
     * Method: paySettle(Result result, String openPlatformNo)
     */
    @Test
    public void testPaySettle() throws Exception {
        Result result = getResult ();
        String openPlatFormNo = "";
        this.payChargeService.paySettle (result, openPlatFormNo);
        System.err.println (openPlatFormNo);
        System.err.println (result.getData ());
    }

    @Test
    public void testCompositeRechargeBack() throws Exception {
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        userInfo.setUserId (2L);
        userInfo.setBizId (-1L);
        result.setUserInfo (userInfo);

        ResultNotifyRequest resultNotifyRequest = new ResultNotifyRequest ();
        resultNotifyRequest.setOutOrderNo ("SP201610271812184862");
        resultNotifyRequest.setPayResult (PayResultType.CHARGING_SUCCESS.getCode ());      //订单结果
        resultNotifyRequest.setResultMsg ("测试成功");    //通知结果描述信息

        result = this.payChargeService.rechargeBack (result, resultNotifyRequest);
        System.err.println (JSONObject.toJSONString (resultNotifyRequest));
        System.err.println (result.getData ());

        //    ####################
        this.payChargeService.paySettle (result, resultNotifyRequest.getOutOrderNo ());
        System.err.println (resultNotifyRequest.getOutOrderNo ());
        System.err.println (result.getData ());
    }

    
    /**
     * Method: chargeRefund(Result result, String chargeCode)
     */
    @Test
    public void testChargeRefund() throws Exception {
        String chargeCode = "DJDF20161119163547857810793";
        Result result = this.getResult ();
        result = this.payChargeService.refundCharge (result, chargeCode,false);
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    @Test
    public void testThread() {
        Result result = getResult ();
        ChargePayHttpLog chargePayHttpLog = new ChargePayHttpLog ();
        chargePayHttpLog.setLogCode ("QQYC");
        chargePayHttpLog.setChargeCode ("11111111");
        chargePayHttpLog.setReceiveMothed ("test");
        chargePayHttpLog.setSendTime (new Date ());
        chargePayHttpLog.setReceiveTime (new Date ());
        chargePayHttpLog.setReceiveData ("测试接收的数据");
        chargePayHttpLog.setSendData ("测试发送的数据");
        chargePayHttpLog.setRemark ("日志记录");
        MongoHttpLogTask httpLogTask = new MongoHttpLogTask (result, chargePayHttpLog);
        WorkerGroup.submit (httpLogTask);
    }

    /**
     * Method: findByPayStatusAndTime(Result result, Integer chargeStatus, Integer beforeDay)
     */
    @Test
    public void testFindByPayStatusAndTime() throws Exception {
        Result result = new Result ();
        result = this.payChargeService.findByPayStatusAndTime (result, ChargePayStatus.PAY_SUCCESS.getCode (), 3);
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: printChargeInfo(Result result, String chargeCode)
     */
    @Test
    public void testPrintChargeInfo() throws Exception {
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        userInfo.setSessionId ("auc_jsession_b3f9340a6b8c40d9a856476490af0d4e839d1a29");
        userInfo.setUserId (2L);
        userInfo.setBizId (11L);
        result.setUserInfo (userInfo);

        String chargeCode = "DJDF20161102215558674235742";

        result = this.payChargeService.printChargeInfo (result, chargeCode);
        System.err.println (chargeCode);
        System.err.println (JSONObject.toJSONString (result.DTO ()));
    }

    /**
     * Method: updateChargeRefundStatus(Result result, String chargeCode, Integer refundStatus)
     */
    @Test
    public void testUpdateChargeRefundStatus() throws Exception {
        String chargeCode = "3001";
        Result result = getResult ();
        result = this.payChargeService.updateChargeRefundStatus (result, chargeCode, ChargeRefundStatus.REFUND.getCode ());
        System.err.println (JSONObject.toJSONString (result));
    }

    /**
     * Method: updateChargeStatus(Result result, String chargeCode, Integer payStatus)
     */
    @Test
    public void testUpdateChargeStatus() throws Exception {
        String chargeCode = "3001";
        Result result = getResult ();
        result = this.payChargeService.updateChargeStatus (result, chargeCode, ChargePayStatus.CHARGE_FAIL.getCode ());
        System.err.println (JSONObject.toJSONString (result));
    }

    /**
     * Method: queryRefundByChargeCode(Result result, String refundCode)
     */
    @Test
    public void testQueryRefundByChargeCode() throws Exception {
        Result result = getResult ();
        String refundCode = "TD-DJDF20161102154236792279227";
        result = this.payChargeService.queryRefundByChargeCode (result, refundCode);
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: queryByChargeCode(Result result, String chargeCode)
     */
    @Test
    public void testQueryByChargeCode() throws Exception {
        Result result = getResult ();
        String chargeCode = "DJDF20161102154236792279227";
        result = this.payChargeService.queryByChargeCode (result, chargeCode);
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }
}

