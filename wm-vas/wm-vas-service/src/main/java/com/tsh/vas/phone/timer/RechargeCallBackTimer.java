/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.timer;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsh.vas.phone.facade.TimerService;


/**
 * 针对于第三方服务没有调用callback url的，我们每隔4分钟主动去查询一次
 * 
 * 场景：供应平台’‘， 我们的平台 都有可能存在网络异常。供应平台发送了，我们这边没收到。或者供应商平台由于内部错误，没有主动给我们发通知。
 * 这个时候，我们需要手动根据 ’订单号‘ 去过去当前’订单‘的状态。做数据更新，同时进行重试调用机制。
 * 
 * @author zengzw
 * @date 2017年3月13日
 */
public class RechargeCallBackTimer {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RechargeCallBackTimer.class);


    @Autowired
    private TimerService timerService;

    public void start(){
        logger.info("#phone------------callbacktimer----->查询5分钟充值没回调订单开始");
        
        timerService.dealWithNoCallBackOrderInfo();
        
        logger.info("#phone----------callbacktimer----->查询5分钟充值没回调订单结束");
    }



}
