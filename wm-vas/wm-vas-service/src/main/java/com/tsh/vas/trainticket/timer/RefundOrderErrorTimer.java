/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsh.vas.trainticket.service.OrderTimerService;

/**
 * 针对于出票失败、购票失败、支付异常进行退款操作
 * 
 * 每2分钟刷新执行一次，2小时后退款
 * 
 *
 * @author zengzw
 * @date 2016年12月1日
 */
public class RefundOrderErrorTimer {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoT3Timer.class);

    @Autowired
    OrderTimerService timerService;

    public void autoRun() {
        logger.info("----------【购票失败】退款定时器开始----------------");

        timerService.detalWithByErrorAndOutTicketError();

        logger.info("----------【购票失败】退款定时器结束----------------");
        
        
        
        
        logger.info("----------【支付异常】退款定时器开始----------------");
        
        timerService.detalWithByPayError();
        
        logger.info("----------【支付异常】退款定时器结束----------------");
        
        
        
    }




    
}
