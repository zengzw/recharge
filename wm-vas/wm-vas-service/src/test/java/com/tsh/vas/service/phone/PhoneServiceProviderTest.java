/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.phone.service.PhoneServiceProviderService;
import com.tsh.vas.service.BaseCaseTest;


/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
public class PhoneServiceProviderTest extends BaseCaseTest{

    @Autowired
    PhoneServiceProviderService providerService;
    
    @Test
    public void test() {
    	Result result = new Result();
    	result = providerService.batchQueryPhoneServiceProviderByIds(result, "'2','3'");
    	System.out.println(result);
    }

    
   
}
