/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.vas.commoms.utils.PhoneUtils;
import com.tsh.vas.phone.facade.QueueRegisterSupplier;
import com.tsh.vas.service.BaseCaseTest;


/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
public class RegisterSupplierManagerTest extends BaseCaseTest{

    @Autowired
    private QueueRegisterSupplier service;

    String key = PhoneUtils.getStoreRedisKey("175", "15820784340");

    /**
     * 查询手机类型
     * @throws Exception 
     * 
     * 
     */
    @Test
    public void testSertServiceResult() throws Exception{
        List<ServiceRegisterVo> listResult = service.setAvaliableSupplier(key, 175L);

        System.out.println(JSON.toJSONString(listResult));
    }

    @Test
    public void getRegisterService(){
        ServiceRegisterVo registerVo =  service.getAvaliableSupplier(key);
        System.out.println(JSON.toJSONString(registerVo)
                );
    }
    
    @Test
    public void deleteStoreKey(){
         service.cleanQueue(key);
    }

}
