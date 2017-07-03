/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.phone.service.PhoneGoodsService;
import com.tsh.vas.service.BaseCaseTest;


/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */

public class PhoneGoodsServiceTest extends BaseCaseTest{

    @Autowired
    PhoneGoodsService phoneGoodsService;
    
    @Test
    public void test() {
    	Result result = new Result();
    	result = phoneGoodsService.getPhoneWaysCount(result, "1,2", "50,60", 
    			"深圳", "506129");
    	System.out.println(result);
    }
    
    @Test
    public void test2() {
    	Result result = new Result();
    	try {
			result = phoneGoodsService.getPhoneGoodsByValueAndCode(result, "'100'", "'YDCZ'");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
   
}
