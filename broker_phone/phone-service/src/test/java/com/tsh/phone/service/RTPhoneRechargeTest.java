/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.po.PhoneRTRechargeProduct;
import com.tsh.phone.recharge.rtpay.request.RTPayRechargeRequest;
import com.tsh.phone.service.impl.PhoneRTProductService;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.phone.vo.recharge.RequestReverseVo;


/**
 * 欧飞 供应商 接口测试
 * 
 * 欧飞测试回调接口，比较让他们修改当前订单状态后，才会回调给我们。
 *
 * @author zengzw
 * @date 2016年7月29日
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:/applicationContext-service.xml"})
public class RTPhoneRechargeTest {



    @Autowired
    RTPayRechargeRequest rechargeRequest;


    @Resource(name="rtRecharge")
    IPhoneRechargeService rechargeService;
    
    
    @Autowired
    PhoneRTProductService productServie;



    /**
     * 查询手机归属地
     */
    @Test
    public void testQueryPhoneType(){
        RequestQueryPhoneTypeVo reqParams = new RequestQueryPhoneTypeVo();
        reqParams.setMobileNum("15917169858");
        ResponseResult result = rechargeService.queryPhoneType(reqParams);

        System.out.println(JSON.toJSONString(result));
    }


    /**
     * 根据订单号查询订单信息
     * 
     */
    @Test
    public void testQueryOrderById(){

        RequestOrderInfoVo reqParams = new RequestOrderInfoVo();
        reqParams.setOrderId("RT201705081659347116");

        ResponseResult responseResult = rechargeService.queryOrderById(reqParams);
        System.out.println(JSON.toJSONString(responseResult));

    }



    /**
     * 充值
     */
    @Test
    public void testRecharage(){
        RequestRechargeVo reqParams = new RequestRechargeVo();
        reqParams.setMobileNum("15811824071");
        //       reqParams.setOrderNo("13242807092");
        reqParams.setPrice(30);
        reqParams.setProvinceName("广东");
        reqParams.setSupplierType("移动");

        ResponseResult result = rechargeService.recharge(reqParams);
        System.out.println(JSON.toJSONString(result));
        
        while(true){
            
        }
        
    }


    /**
     * 取消
     */
    @Test
    public void testReversal(){
        RequestReverseVo reqParams = new RequestReverseVo();
        reqParams.setMobileNum("15811824071");
        reqParams.setOrderNo("RT201705081707393686");
        reqParams.setPrice(30);
        reqParams.setProvinceName("广东");
        reqParams.setSupplierType("移动");

        ResponseResult result = rechargeService.reversal(reqParams);
        System.out.println(JSON.toJSONString(result));
    }



    /**
     * 测试本地回调
     * 
     */
    @Test
    public void testLocalCallback(){
        String orderId = "IP201703141735020383";
        int status = 1; //0 成功，1 失败
        String msg = "成功";

        String result =  rechargeRequest.callback(orderId, status, msg);

        System.out.println(result);
    }

    
    @Test
    public void queryProduct(){
        String provinceId = "广东";
        String supplierType = "移动";
        int price =30;
        PhoneRTRechargeProduct phoneRechargeProduct = productServie.getProductOfMobilePhone(provinceId, supplierType, price);
        
        System.out.println(JSON.toJSONString(phoneRechargeProduct));
    }
}
