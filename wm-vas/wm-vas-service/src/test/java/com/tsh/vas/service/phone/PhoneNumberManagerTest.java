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

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.phone.service.VasMobileManagerService;
import com.tsh.vas.service.BaseCaseTest;

/**
 *
 * @author zengzw
 * @date 2017年5月3日
 */

public class PhoneNumberManagerTest extends BaseCaseTest{
    
    @Autowired
    private VasMobileManagerService mobileManagerService;
    
    
    @Test
    public void testQueryByMobileNum(){
        
        String mobileNum = "15811824071";
        Result result = this.getResult();
        mobileManagerService.queryByMobilenum(result, mobileNum);
        
        System.out.println(JSON.toJSONString(result.getData()));
    }

}
