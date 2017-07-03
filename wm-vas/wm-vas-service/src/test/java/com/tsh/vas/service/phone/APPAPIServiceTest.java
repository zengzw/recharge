/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.fina.metaq.vo.fund.MQBizOrderPay;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.phone.enums.EnumActivetyRewardStatus;
import com.tsh.vas.phone.enums.EnumServiceProviceType;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.manager.CouponComparetor;
import com.tsh.vas.phone.service.VasPhoneOneyuanFreeService;
import com.tsh.vas.phone.vo.ReqQueryActivityOrderListParams;
import com.tsh.vas.phone.vo.ReqQueryOrderListParams;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;


/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
public class APPAPIServiceTest extends BaseCaseTest{

    @Autowired
    APIAppPhoneService apiService;


    @Autowired
    VasPhoneOneyuanFreeService activetyService;

    @Test
    public void testRecharge(){
        Result result = getResult();
        String orderCode = "999999999";
        apiService.recharge(result, orderCode);

    }



    /**
     * 获取默认面值列表
     */
    @Test
    public void testQueryDefaultPhoneValue(){
        Result result = getResult();
        apiService.queryListDefaultPhoneValue(result);

        System.out.println("--"+JSON.toJSONString(result.getData()));
    }



    /**
     * 获取默认面值列表
     */
    @Test
    public void testQueryLimitPhoneSegment(){
        Result result = getResult();
        apiService.queryLimitPhoneSegment(result);

        System.out.println("--"+JSON.toJSONString(result.getData()));
    }


    /**
     * 获取默认面值列表
     */
    @Test
    public void testQueryCurrentMobilePhoneValue(){
        Result result = getResult();
        String mobileNum = "15811824071";
        apiService.queryCurrentMobilePhoneValue(result, mobileNum);

        System.out.println("--"+JSON.toJSONString(result.DTO()));
    }




    /**
     *  支付下单
     */
    @Test
    public void testPayChargeInfo() throws Exception {
        //网点代付
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        //        userInfo.setUserId (163L);
        userInfo.setUserId (213L);
        userInfo.setBizId (51L);
        result.setUserInfo (userInfo);
        PhoneOrderInfoVo orderInfoVo = new PhoneOrderInfoVo();
        orderInfoVo.setMobile ("15811824071");
        orderInfoVo.setBusinessCode(EnumBusinessCode.MPCZ.name());
        orderInfoVo.setSubBusinessCode(EnumServiceProviceType.YDCZ.name());
        orderInfoVo.setOriginalAmount (new BigDecimal (98)); //应付金额
        orderInfoVo.setRealAmount (new BigDecimal (98)); //实际支付金额
        orderInfoVo.setSaleAmount(BigDecimal.valueOf(100));
        orderInfoVo.setBizType (RoleType.SHOP.getCode ());
        orderInfoVo.setUserName("tsh_wb04");
        orderInfoVo.setPayPassword ("123456");
        orderInfoVo.setRechargePhone("15917169858");
        orderInfoVo.setGoodsId("20");

        result = this.apiService.payOdrerInfo(result, orderInfoVo);
        System.err.println (JSONObject.toJSONString (result.DTO()));
    }



    /**
     *  支付下单
     */
    @Test
    public void testGXPayChargeInfo() throws Exception {
        //网点代付
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        //        userInfo.setUserId (163L);
        userInfo.setUserId (213L);
        userInfo.setBizId (51L);
        userInfo.setToken("mbr_tk_142_19337_014dd72cf3e5452286c1c34305c45384");
        result.setUserInfo (userInfo);
        PhoneOrderInfoVo orderInfoVo = new PhoneOrderInfoVo();
        orderInfoVo.setMobile ("15811824071");
        orderInfoVo.setBusinessCode(EnumBusinessCode.MPCZ.name());
        orderInfoVo.setSubBusinessCode(EnumServiceProviceType.YDCZ.name());
        orderInfoVo.setOriginalAmount (new BigDecimal (98)); //应付金额
        orderInfoVo.setRealAmount (new BigDecimal (98)); //实际支付金额
        orderInfoVo.setSaleAmount(BigDecimal.valueOf(100));
        orderInfoVo.setBizType (RoleType.SHOP.getCode ());
        orderInfoVo.setUserName("tsh_wb04");
        orderInfoVo.setPayPassword ("123456");
        orderInfoVo.setRechargePhone("15917169858");
        orderInfoVo.setGoodsId("20");
        orderInfoVo.setOpenUserId("111111111111");
        //        orderInfoVo.setCouponId(22);

        result = this.apiService.payGXOdrerInfo(result, orderInfoVo);
        System.err.println (JSONObject.toJSONString (orderInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }


    @Test
    public void testOrderInfo(){
        String orderCode = "MPCZ20170310183031572673051";
        Result result = getResult();
        apiService.queryRechargeOrderInfo(result, orderCode);

        System.out.println(JSON.toJSONString(result.getData()));
    }




    @Test
    public void testQueryPhoneList(){
        Result result = getResult();
        ReqQueryOrderListParams params = new ReqQueryOrderListParams();
        params.setPage(1);
        params.setRows(10);
        params.setSearchInfo("MPCZ20170309173527606978344");
        //-1:全部；1：等待结果，2：已成功，3：已失败
        params.setType("3"); //等待结果
        apiService.queryOrderList(result, params);

        System.out.println(JSON.toJSONString(result.getData()));
    }


    @Test
    public void testQueryOrderDetail(){
        Result result = getResult();

        String orderCode = "MPCZ20170310182803617729371";
        apiService.queryOrderDetail(result, orderCode);

        System.out.println(JSON.toJSONString(result.getData()));
    }


    /**
     * 支付成功回调 
     * @throws Exception 
     */
    @Test
    public void testRechargeSuccessCallBack() throws Exception{
        Result result = getResult();
        MQBizOrderPay pay = new MQBizOrderPay();
        pay.setBizOrderNo("MPCZ20170317170311549479289");
        apiService.paySuccessCallBack(result, pay);
    }


    @Test
    public void addActivetyOrderInfo(){
        for(int i = 1; i<20; i++){
            Result result = getResult();
            VasPhoneOneyuanFreeVo activety = new VasPhoneOneyuanFreeVo();
            activety.setOrderCode("00001"+i);
            activety.setChargeAmount(BigDecimal.valueOf(22));
            activety.setChargeMobile("15917169858");
            activety.setActivityAmount(1);
            activety.setCreateTime(new Date());
            activety.setCheckStatus(EnumActivetyAuditStatus.DEFAULT.getType());
            activety.setLotteryStatus(EnumActivetyRewardStatus.DEFAULT.getType());
            activety.setBizType(EnumBusinessCode.MPCZ.getBusinessName());
            activety.setLotteryStatus(EnumActivetyRewardStatus.PASS.getType());
            activety.setLotteryTime(DateUtil.addTime(new Date(),i));
            activety.setAreaId(i+1);
            activety.setAreaName("n南山店"+i);
            activety.setStoreId(i+2);
            activety.setStoreName("南山县域"+i);

            activetyService.addVasPhoneOneyuanFree(result, activety);
        }
    }


    /**
     * 查询活动列表
     */
    @Test
    public void searchAcitivtyOrderInfoList(){
        Result result = getResult();
        ReqQueryActivityOrderListParams params = new ReqQueryActivityOrderListParams();
        params.setPage(1);
        params.setRows(5);
        params.setType("1");
        params.setSearchInfo("15811824071");
        apiService.queryActivityOrderList(result, params);

        System.out.println(JSON.toJSONString(result.getData()));
    }



    @Test
    public void addActivity(){
        Result result = getResult();
        PhoneOrderInfoPo phoneOrderInfoPo = new PhoneOrderInfoPo();
        phoneOrderInfoPo.setPhoneOrderCode("00001");
        phoneOrderInfoPo.setSaleAmount(BigDecimal.valueOf(100));
        phoneOrderInfoPo.setRechargePhone("15811824011");
        phoneOrderInfoPo.setStoreCode("2222");
        phoneOrderInfoPo.setStoreName("22222");
        phoneOrderInfoPo.setCountryCode("22223");
        phoneOrderInfoPo.setCountryName("22224");
        //        apiService.addActivityOrder(result, phoneOrderInfoPo);   
    }



    @Test
    public  void testCompare() {
        List<CouponVO> listResult  = new ArrayList<>();

        CouponVO couponVO = new CouponVO();
        couponVO.setCouponStatus(1);
        couponVO.setRecordID(11L);
        couponVO.setMoney(BigDecimal.valueOf(5));
        couponVO.setUseBeginTime("2017-06-20");
        couponVO.setUseEndTime("2017-07-21");
        couponVO.setUseScope("全场通用");
        listResult.add(couponVO);

        CouponVO couponVO1 = new CouponVO();
        couponVO1.setCouponStatus(1);
        couponVO1.setRecordID(20L);
        couponVO1.setMoney(BigDecimal.valueOf(30));
        couponVO1.setUseBeginTime("2017-06-20");
        couponVO1.setUseEndTime("2017-08-20");
        couponVO1.setUseScope("全场通用");
        listResult.add(couponVO1);


        CouponVO couponVO2 = new CouponVO();
        couponVO2.setCouponStatus(1);
        couponVO2.setRecordID(31L);
        couponVO2.setMoney(BigDecimal.valueOf(20));
        couponVO2.setUseBeginTime("2017-06-20");
        couponVO2.setUseEndTime("2017-07-20");
        couponVO2.setUseScope("全场通用");
        listResult.add(couponVO2);

        System.out.println(JSON.toJSONString(listResult));

        Collections.sort(listResult,new CouponComparetor());

        System.out.println("after:"+JSON.toJSONString(listResult));
    }

}
