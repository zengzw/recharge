/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.commoms.queue.DBLogQueue;
import com.tsh.vas.model.ChargePayHttpLog;
import com.tsh.vas.service.ChargePayHttpLogService;

/**
 *
 * @author zengzw
 * @date 2017年5月25日
 */
public class PayLogService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(PayLogService.class); 

    @Autowired
    ChargePayHttpLogService logService;
    
//    private ScheduledExecutorService ScheduledService = Executors.newScheduledThreadPool(corePoolSize)

    @Override
    public void afterPropertiesSet() throws Exception {
        final DBLogQueue<ChargePayHttpLog> queue = new DBLogQueue<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(!queue.isEmpty()){
                        ChargePayHttpLog log =  queue.pool();
                        logger.info("#--->消费了日志消息进行入库：{}",JSON.toJSONString(log));
                        try {
                            logService.insert(new Result(), log);
                        } catch (Exception e) {
                          logger.error("新增日志失败:",e);
                        }
                    }
                }

            }
        }).start();
    }
}
