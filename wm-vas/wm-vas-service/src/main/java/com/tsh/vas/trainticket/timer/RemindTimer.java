package com.tsh.vas.trainticket.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsh.vas.trainticket.service.OrderTimerService;
/**
 * 发车短信提醒(每天12:00提醒用户前一天取票发车)
 * @see 
 * @since JDK 1.7.0
 */
public class RemindTimer {
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoT3Timer.class);

    
    @Autowired
    OrderTimerService timerService;
    
    public void autoRun() {
        logger.info("----------【乘车人短信提醒】主动查询定时器开始----------------");
        
        timerService.startToRemind();
        
        logger.info("----------【乘车人短信提醒】主动查询定时器结束----------------");
    }
}

