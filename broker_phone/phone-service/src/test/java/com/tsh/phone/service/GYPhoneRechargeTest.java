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
import com.tsh.phone.commoms.config.OFRechargeConfig;
import com.tsh.phone.recharge.gaoypay.request.GaoYPayRechargeRequest;
import com.tsh.phone.vo.ResponseResult;
import com.tsh.phone.vo.recharge.RequestCallbackVo;
import com.tsh.phone.vo.recharge.RequestOrderInfoVo;
import com.tsh.phone.vo.recharge.RequestQueryPhoneTypeVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;


/**
 *  高阳 供应商 接口测试
 *  
 *  高阳手动回调，自己手动触发。地址（IE）：http://219.143.36.227:101/dealer/pages/testNotify.jsp
 *  
 * @author zengzw
 * @date 2016年7月29日
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:/applicationContext-service.xml"})
public class GYPhoneRechargeTest {


    @Autowired
    OFRechargeConfig config;


    @Autowired
    GaoYPayRechargeRequest rechargeRequest;


    @Resource(name="gyRecharge")
    IPhoneRechargeService rechargeService;


    @Test
    public void loadConfig() throws InterruptedException{
        System.out.println(config.getVersion());

        Thread.sleep(1000 * 60);

        System.out.println(config.getVersion());

    }



    /**
     * 查询手机归属地
     */
     @Test
     public void testQueryPhoneType(){
         RequestQueryPhoneTypeVo reqParams = new RequestQueryPhoneTypeVo();
         reqParams.setMobileNum("15811824071");
         ResponseResult result = rechargeService.queryPhoneType(reqParams);
         
         System.out.println(JSON.toJSONString(result));
     }
     
     /**
      * 查询手机归属地
      */
     @Test
     public void testQueryErrorPhoneType(){
         RequestQueryPhoneTypeVo reqParams = new RequestQueryPhoneTypeVo();
         reqParams.setMobileNum("17058965874");
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
//         reqParams.setOrderId("GY201608231401114273");
         reqParams.setOrderId("GY2016082314011142733");
         
         ResponseResult responseResult = rechargeService.queryOrderById(reqParams);
         System.out.println(JSON.toJSONString(responseResult));
         
     }
     
     
     
     /**
      * 充值
      */
     @Test
     public void testRecharage(){
         RequestRechargeVo reqParams = new RequestRechargeVo();
//         reqParams.setMobileNum("15917169858");
//         reqParams.setMobileNum("13651658248"); //上海
//         reqParams.setMobileNum("13401028888"); //北京
         
         // =========== 电信 ======
         reqParams.setMobileNum("15313321931");
         
         reqParams.setPrice(100);
         
         ResponseResult result = rechargeService.recharge(reqParams);
         System.out.println(JSON.toJSONString(result));
     }
     
     
     /**
      * 测试本地回调
      * 
      */
     @Test
     public void testLocalCallback(){
         RequestCallbackVo reqeust = new RequestCallbackVo();
         reqeust.setStatus(4);
         reqeust.setOrderId("GY2016082314011142733");
         rechargeService.callback(reqeust);
        
     }
      

}
