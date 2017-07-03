/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tsh.phone.po.PhoneRTBusinessPo;
import com.tsh.phone.service.impl.PhoneRTBusinessService;


/**
 *  高阳 供应商 接口测试
 *  
 *  高阳手动回调，自己手动触发。地址（IE）：http://219.143.36.227:101/dealer/pages/testNotify.jsp
 *  
 * @author zengzw
 * @date 2016年7月29日
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:/applicationContext-service.xml"})
public class RTBusinessCodeTest {

    
    @Autowired
    PhoneRTBusinessService service;


    @Test
    public void testQueryBusinessCode() throws InterruptedException{
     
        String province = "广东";
        String supplierType = "移动1";
        
        PhoneRTBusinessPo phoneRTBusinessPo =  service.queryBusinessCode(province, supplierType);
        System.out.println(JSON.toJSONString(phoneRTBusinessPo));

    }



}
