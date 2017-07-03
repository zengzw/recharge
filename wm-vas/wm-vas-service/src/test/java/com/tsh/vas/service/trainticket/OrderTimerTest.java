/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.trainticket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.trainticket.service.OrderTimerService;

/**
 *
 * @author zengzw
 * @date 2016年12月8日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class OrderTimerTest extends BaseCaseTest{
    
    
    @Autowired
    OrderTimerService timerService;
    
    /**
     *  正在查询正在支付中的订单，根据状态，生成退款单
     */
    @Test
    public void testPaying(){
        timerService.detalWithPayding();
    }
    
    
    
    /**
     * 查询正在退款中的订单
     */
    @Test
    public void refunding(){
        timerService.detalWithRefunding();
    }

    
    
    /**
     * 测试支付异常退款的【退款操作】
     */
    @Test
    public void detalWithByPayException(){
        timerService.detalWithByPayError();
    }
    
    
    
    /**
     * 购票失败 退款退分润的
     */
    @Test
    public void detalWithByErrorAndOutTicketError(){
        timerService.detalWithByErrorAndOutTicketError();
    }
    
    
    /**
     * 查询退票成功，需要退款 不退分润的订单
     */
    @Test
    public void detalWithReturnTicket(){
        timerService.detalWithReturnTicket();
    }
    
}
