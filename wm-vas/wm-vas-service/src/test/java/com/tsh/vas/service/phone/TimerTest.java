/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.json.JSON;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.phone.facade.TimerService;

/**
 *
 * @author zengzw
 * @date 2017年3月13日
 */
public class TimerTest {

    
    @Autowired
    TimerService timerService;
    
    
    /**
     * 消息没有回调
     * 
     */
    @Test
   public void testQueryRechargingOrder(){
       timerService.dealWithNoCallBackOrderInfo();
   }
    
    
    
    /**
     * 退款
     */
    @Test
    public void testReturnRefundOrder(){
        timerService.detalWithByUnRefundOrder();
    }
    

    /**
     * 退款
     */
    @Test
    public void testTest(){
        timerService.detalWithByUnRefundOrder();
    }
    
    
    /**
     * 支付超时 
     */
    @Test
    public void testPayPending(){
        timerService.dealWithPadingOrderInfo();
    }

    public static void main(String[] args) {
        com.alibaba.fastjson.JSON.toJSONString(null);
    }
}
