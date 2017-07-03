/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.phone.service.VasPhoneOneyuanFreeService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.phone.QueryActivityStatisticsVo;

/**
 *
 * @author zengzw
 * @date 2017年4月20日
 */
public class VasPhoneOneyuanFreeServiceTest extends BaseCaseTest{

    @Autowired
    VasPhoneOneyuanFreeService service;
    
    @Test
    public void searchAcitivtyOrderInfoList(){
        Result result = getResult();
        String orderCode = "0000119";
        
        service.updateAuditStatus(result, orderCode, new Date(), EnumActivetyAuditStatus.PASS.getType());
        
        System.out.println(JSON.toJSONString(result.getData()));
    }
    
    
    @Test
    public void queryPageActivityStatistics(){
        Result result = this.getResult();
        QueryActivityStatisticsVo param = new QueryActivityStatisticsVo();
        param.setPage(1);
        param.setRows(10);
        
        param.setBeginTime("2017-04-28");
        
        
        service.queryPageActivityStatistics(result, param);
        
        System.out.println(JSON.toJSONString(result.getData()));
    }
}
