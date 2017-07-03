/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.dubbo.hlq.enumType.CouponStatusEnum;
import com.tsh.vas.iservice.IMemberService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.service.CouponService;
import com.tsh.vas.vo.coupon.QueryCouponParams;
import com.tsh.vas.vo.phone.MemberVo;

/**
 *
 * @author zengzw
 * @date 2017年6月26日
 */
public class CouponApiServiceTest extends BaseCaseTest{

    @Autowired
    CouponService couponService;
    
    @Autowired
    IMemberService memberService;
    
    private static String mobile = "15811824071";
    
    @Test
    public  void getListCoupon() throws FunctionException{
        Result result = getResult();
        QueryCouponParams params = new QueryCouponParams();
        params.setMoney(20D);
        params.setOrderNo("15811824071");
        MemberVo memb = memberService.getMemberInfoForApp(mobile, mobile, result.getUserInfo().getToken());
        params.setUserId(memb.getId().intValue());
        params.setShopId(result.getUserInfo().getBizId());
        
        System.out.println(couponService.getListCoupon(result, params));
        
        
    }
    
    
    /**
     * 使用优惠券
     * @throws FunctionException 
     */
    @Test
    public void applyCoupon() throws FunctionException{
        Result result = getResult();
        QueryCouponParams params = new QueryCouponParams();
        params.setCouponIds(new Integer[]{100});
        params.setMoney(50D);
        params.setOrderNo("td-333333333333");
        params.setShopId(result.getUserInfo().getBizId());
        MemberVo memb = memberService.getMemberInfoForApp(mobile, mobile, result.getUserInfo().getToken());
        params.setUserId(memb.getId().intValue());
        
        couponService.applyCoupon(result,params);
    }
    
    
    /**
     * 修改优惠券Id
     * 
     * @throws Exception
     */
    @Test
    public void updateCouponStatusByIds() throws Exception{
        Result result = getResult();
        Long couponId = 4024L;
        couponService.updateCouponStatusById(result, couponId, CouponStatusEnum.NOT_USE);
    }

}
