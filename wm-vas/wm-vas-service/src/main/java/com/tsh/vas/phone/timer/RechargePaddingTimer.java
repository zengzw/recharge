/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.timer;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsh.vas.phone.facade.TimerService;


/**
 * 针对与故乡 30分钟还在支付中的订单。修改位支付失败。
 * 
 * @author zengzw
 * @date 2017年3月13日
 */
public class RechargePaddingTimer {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RechargePaddingTimer.class);


    @Autowired
    private TimerService timerService;

    public void start(){
        logger.info("#phone------------PaddingTimer----->查询30分钟还没回调订单开始");
        
        timerService.dealWithPadingOrderInfo();
        
        logger.info("#phone----------PaddingTimer----->查询30分钟还没回调订单结束");
    }



}
