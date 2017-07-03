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
 * 用户手动退票，退款操作
 * 
 * 6小时退款、退分润
 * 
 *
 * @author zengzw
 * @date 2016年12月1日
 */
public class ReturnTicketTimer {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoT3Timer.class);
    
    @Autowired
    OrderTimerService timerService;

    public void autoRun() {

        logger.info("---------------用户【退票退款】定时任务开始-------------");
        
        timerService.detalWithReturnTicket();
        
        logger.info("---------------用户【退票退款】定时任务结束-------------");
    }



}
