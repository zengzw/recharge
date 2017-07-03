/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.phone;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.phone.enums.EnumServiceProviceType;
import com.tsh.vas.phone.manager.AbstOrderInfoManagerService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;

/**
 *
 * @author zengzw
 * @date 2017年5月27日
 */

public class PhoneStrategyTest extends BaseCaseTest{
    
    
   @Resource(name="tshOrderInfoService")
    AbstOrderInfoManagerService service;
    
    @Test
    public void test() throws Exception{
    
        System.out.println(">>>>>>>>>>>>>>>>>>> "+service);
        Result result = getResult();
        //网点代付
        UserInfo userInfo = new UserInfo ();
        //        userInfo.setUserId (163L);
        userInfo.setUserId (213L);
        userInfo.setBizId (51L);
        result.setUserInfo (userInfo);
        PhoneOrderInfoVo orderInfoVo = new PhoneOrderInfoVo();
        orderInfoVo.setMobile ("15811824071");
        orderInfoVo.setBusinessCode(EnumBusinessCode.MPCZ.name());
        orderInfoVo.setSubBusinessCode(EnumServiceProviceType.YDCZ.name());
        orderInfoVo.setOriginalAmount (new BigDecimal (98)); //应付金额
        orderInfoVo.setRealAmount (new BigDecimal (98)); //实际支付金额
        orderInfoVo.setSaleAmount(BigDecimal.valueOf(100));
        orderInfoVo.setBizType (RoleType.SHOP.getCode ());
        orderInfoVo.setUserName("zengzw");
        orderInfoVo.setPayPassword ("123456");
        orderInfoVo.setRechargePhone("15917169858");
        orderInfoVo.setGoodsId("20");
       PhoneOrderInfoPo phoneOrderInfoPo =  service.createOrder(result, orderInfoVo);
       
       System.out.println(JSON.toJSONString(phoneOrderInfoPo));
       
       service.pay(result, phoneOrderInfoPo);
    }

}
