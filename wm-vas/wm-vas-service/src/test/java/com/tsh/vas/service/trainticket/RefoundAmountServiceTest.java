/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.trainticket;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.service.HcpRefundAmountService;
import com.tsh.vas.vo.trainticket.HcpRefundAmountVo;

/**
 *
 * @author zengzw
 * @date 2016年11月29日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class RefoundAmountServiceTest extends BaseCaseTest{


    @Autowired
    HcpRefundAmountService service;

    @Test
    public void testSave() throws Exception{
        Result result = this.getResult();
        
        HcpRefundAmountVo refundAmountPo = new HcpRefundAmountVo();
        refundAmountPo.setCreateTime(new Date());
        refundAmountPo.setHcpOrderCode("2124");
        refundAmountPo.setRefundAmountCode("232234");
        refundAmountPo.setPayWay(1);
        refundAmountPo.setRefundTime(new Date());
        refundAmountPo.setRefundType(1);
        
        service.addHcpRefundAmount(result, refundAmountPo);
    }
    
    
    @Test
    public void updateCount(){
        Result result = this.getResult();
        
        service.updateRefundRquestTime(result, "33", 20);
    }
    
    @Test
    public void updateStatus(){
        Result result = this.getResult();
        String code = "33";
        service.updateRefundStatus(result,  code, EnumRefundOrderStatus.REFUND_FAIL.getCode());
    }
    
    @Test
    public void search(){
        Result result = this.getResult();
        //处理时间3天
        Integer beforeMinuteTime = -120;

        Date date = DateUtil.addTime(new Date(), beforeMinuteTime);
       // service.findByStatusWaitOrError(result, date, EnumRefundType.BUY_TICKET_ERROR.getType());
        
        System.out.println(JSON.toJSONString(result.getData()));
    }

}
