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
 * 针对于退款单一直在退款中的退款单订单处理
 * 
 * 退款单中超过1个小时的我们主动去查账户支付状态。
 * 1，如果是失败，修改为退款失败。（走线下退款操作）
 * 2，如果是成功，修改退款成功。
 * 
 * 每2分钟刷新执行一次，针对于1小时没有收到异步通知退款通知（定时器刷新频率可以在LTS上面做调整，实时生效。
 * 
 * TODO 针对于1小时时间，做到灵活的话得配置在动态配置中。
 * 
 *
 * @author zengzw
 * @date 2016年12月1日
 */
public class RefundingTimer {

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoT3Timer.class);

    @Autowired
    OrderTimerService timerService;

    public void autoRun() {
        logger.info("----------【退款中】定时器开始----------------");

        timerService.detalWithRefunding();

        logger.info("----------【退款中】定时器结束----------------");
    }



}
