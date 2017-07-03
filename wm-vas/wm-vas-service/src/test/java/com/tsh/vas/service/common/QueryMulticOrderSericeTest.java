/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.commoms.enums.EnumOrderType;
import com.tsh.vas.service.ChangeMulticOrderService;
import com.tsh.vas.manager.service.ChangeOrderStatusService;
import com.tsh.vas.vo.QueryOrderChangeVO;

/**
 *
 * @author zengzw
 * @date 2017年4月14日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class QueryMulticOrderSericeTest {

    @Autowired
    ChangeMulticOrderService service;
    
    @Autowired
    ChangeOrderStatusService orderService;
    
    @Test
    public void test() throws Exception {
        String orderCode = "MPCZ20170309173527606978344";
        Integer type  = EnumOrderType.MPCZ.getCode();
        
        QueryOrderChangeVO result = service.queryByOrderCode(orderCode, type);
        System.out.println("--------"+JSON.toJSONString(result));
    }
    
    @Test
    public void test1() throws Exception {
        Result result = new Result();
        Page page = new Page<>();
        QueryOrderChangeVO queryOrderChangeVO = new QueryOrderChangeVO();
        orderService.queryChangeOrderList(result, page, queryOrderChangeVO);
        System.out.println("--------"+JSON.toJSONString(result.getData()));
    }
}
