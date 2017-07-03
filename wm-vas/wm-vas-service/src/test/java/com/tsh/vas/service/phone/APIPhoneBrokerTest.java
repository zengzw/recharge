/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.util.bean.Result;
import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.phone.vo.recharge.RequestRechargeVo;
import com.tsh.vas.phone.service.APIPhoneBrokerService;
import com.tsh.vas.service.BaseCaseTest;


/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */

public class APIPhoneBrokerTest extends BaseCaseTest{

    @Autowired
    APIPhoneBrokerService service;
    
  

    /**
     * 查询手机类型
     * 
     * 
     */
    @Test
    public void testQueryPhoneType(){
        final Result result = getResult();
        final String mobileNum = "13510214190";
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                service.queryPhoneLocation(result, mobileNum);
                
            }
        }).start();
      
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                service.queryPhoneLocation(result, mobileNum);
                
            }
        }).start();
        
      
        while(true){}
        
       
       /* 
        PhoneLocationVo phoneLocationVo = result.getData();
        System.out.println(">>>>>>>>"+phoneLocationVo.getCityName());
        System.out.println("---"+JSON.toJSONString(result.getData()));*/
    }


    /**
     * 查询订单类型
     * 
     * 
     */
    @Test
    public void testQueryOrderInfo(){
        Result result = getResult();
        String gyOrderNo = "GY201702271511246042";
        String ofOrderNo = "IP201702271431315985";
        
       // service.queryOrderInfo(result, ofOrderNo);
        
        System.out.println("---"+JSON.toJSONString(result));
    }
    
    @Test
    public void test(){
        RedisSlave.getInstance().del("wmvas_phone_provider");
    }
    
    /**
     * 充值
     * 
     * 
     */
/*   @Test
    public void testRecharge(){
        Result result = getResult();
        RequestRechargeVo rechargeVo = new RequestRechargeVo();
        rechargeVo.setMobileNum("15917169858");
        rechargeVo.setPrice(100);
        
        service.recharge(result, rechargeVo);
        
        System.out.println("---"+JSON.toJSONString(result));
    }
    
    
*/

}
