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
 * 针对于订单一直支付中的订单处理
 * 
 * 支付中超过两个小时的我们主动去查账户支付状态。
 * 1，如果是失败，修改为支付失败。
 * 2，如果是成功，修改位支付异常。生成退款单，进行退款不退分润操作（分润单只有在支付成功回调时才会生成）。
 * 
 * 每2分钟刷新执行一次，针对于2小时没有收到异步通知支付通知
 * 
 *
 * @author zengzw
 * @date 2016年12月1日
 */
public class OrderPayingTimer {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoT3Timer.class);

    @Autowired
    OrderTimerService timerService;

    public void autoRun() {
        logger.info("----------【支付中未回调】主动查询定时器开始----------------");

        timerService.detalWithPayding();

        logger.info("----------【支付中未回调】主动查询定时器结束----------------");
    }

}
