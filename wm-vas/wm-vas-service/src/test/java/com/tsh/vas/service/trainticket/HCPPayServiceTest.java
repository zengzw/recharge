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

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.trainticket.service.HcpOrderInfoService;

/**
 *
 * @author zengzw
 * @date 2016年12月5日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class HCPPayServiceTest extends BaseCaseTest{

    
    @Autowired
    HcpOrderInfoService service;
    
    /**
     * 支付测试
     */
    @Test
    public void pay(){
        Result result = getResult();
        service.queryCurrentDaySameTrainNum(result, "K9018", "2016-12-20", "430524187728322121");
        System.out.println(JSON.toJSONString(result.getData()));
    }
}
