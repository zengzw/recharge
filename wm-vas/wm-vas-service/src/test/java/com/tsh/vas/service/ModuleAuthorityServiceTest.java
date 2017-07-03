/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.cms.RecipientEncryptedKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.phone.service.ModuleAuthorityService;
import com.tsh.vas.vo.ModuleAuthorityVo;
import com.tsh.vas.vo.QueryModuleAuthorityVo;
import com.tsh.vas.vo.ReceiveShopVo;

/**
 *
 * @author zengzw
 * @date 2017年3月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class ModuleAuthorityServiceTest extends BaseCaseTest{

    @Autowired
    ModuleAuthorityService moduleAuthorityService;
    
    @Test
    public void testService(){
        
        Result result = getResult();
        ModuleAuthorityVo moduleAuthorityVo = new ModuleAuthorityVo();
        moduleAuthorityVo.setBusinessCode("YYMD");
        moduleAuthorityVo.setProjectCode("VAS");
        moduleAuthorityVo.setAreaId(39);
        moduleAuthorityService.queryByCombindTerm(result,moduleAuthorityVo);
        
        System.out.println(JSON.toJSONString(result.getData()));
    }
    
    
    @Test
    public void testActivityService(){
        
        Result result = getResult();
        
        moduleAuthorityService.queryPhoneActivityByAreaId(result, result.getUserInfo().getBizId());
        
        System.out.println(JSON.toJSONString(result.getData()));
    }
    
    
    @Test
    public void testPageQuery(){
        Result result  = getResult();
        Page page = new Page(1,10);
        QueryModuleAuthorityVo params = new QueryModuleAuthorityVo();
        params.setSelectStatus(0);
        moduleAuthorityService.queryPagePhoneActivityArea(result, page, params);
        
        System.out.println("-------"+JSON.toJSONString(result.getData()));
        
    }
    
    @Test
    public void testSave(){
        Result result  = getResult();
      
        ModuleAuthorityVo moduleAuthorityVo = new ModuleAuthorityVo();
        moduleAuthorityVo.setBeginTime(DateUtil.addTime(new Date(),20));
        moduleAuthorityVo.setEndTime(DateUtil.addDate(new Date(),0));
        moduleAuthorityVo.setCheckEndTime(DateUtil.addDate(new Date(),2));
        moduleAuthorityVo.setReceiveScore("2");

        ReceiveShopVo receiveShopVo = new ReceiveShopVo();
        receiveShopVo.setAreaId(21);
        receiveShopVo.setAreaName("测试县域");
        
        List<ReceiveShopVo> lstReceiveShopVo = new ArrayList<>();
        lstReceiveShopVo.add(receiveShopVo);
        moduleAuthorityVo.setLstReceiveShop(lstReceiveShopVo);
        
        moduleAuthorityService.saveYYMDActivity(result, moduleAuthorityVo);
        System.out.println("-------"+JSON.toJSONString(result.getData()));
        
    }
    
    
    @Test
    public void testUpdate(){
        Result result  = getResult();
        
        ModuleAuthorityVo moduleAuthorityVo = new ModuleAuthorityVo();
        moduleAuthorityVo.setBeginTime(DateUtil.addTime(new Date(),20));
        moduleAuthorityVo.setEndTime(DateUtil.addDate(new Date(),1));
        moduleAuthorityVo.setCheckEndTime(DateUtil.addDate(new Date(),2));
        moduleAuthorityVo.setUpdateIds("109");
        
        
        moduleAuthorityService.updateYYMDActivity(result, moduleAuthorityVo);
        System.out.println("-------"+JSON.toJSONString(result.getData()));
        
    }
}
