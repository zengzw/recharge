/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tsh.sfund.dto.UseCoupons;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;

/**
 *
 * @author zengzw
 * @date 2017年6月30日
 */
public class FundBuilder {
    
    private FundBuilder(){};
    
    
    /**
     *  构建优惠券信息
     *  
     * @param orderInfo
     * @param orderCoupon
     * @return
     */
    public static List<UseCoupons> buildListCoupons(PhoneOrderInfoPo orderInfo, PhoneUseCouponRecordPo orderCoupon) {
        List<UseCoupons> lstUseCoupons = new ArrayList<>();
        if(orderCoupon == null){
            return lstUseCoupons;
        }
        
        UseCoupons useCoupons = new UseCoupons();
        useCoupons.setCouponId(orderCoupon.getRuleId()); //优惠券规制Id
        useCoupons.setUserCouponId(orderCoupon.getRecordId()); //优惠券Id
        useCoupons.setMerchantUserid(Integer.parseInt(orderCoupon.getMerchantUserId()));//发放 优惠券用户Id
        useCoupons.setCouponRoleType(2); //平台
        useCoupons.setCouponType(Integer.parseInt(orderCoupon.getCouponType())); //惠券金额类型:1，现金券 2，折扣券,

        
        //单位 元转成分
        long couponMoney = BigDecimal.valueOf(Long.parseLong(orderCoupon.getMoney())).multiply(new BigDecimal (100)).longValue();
        useCoupons.setCouponMoney(couponMoney);//优惠券金额
        /*
         * 注意：如果参加活动：应付金额要-1，再计算活动‘实际优惠金额’
         * 
         * 如果 （应付金额  >= 优惠券面额）= 惠券面额。 （应付金额 < 优惠券面额）  = 应付金额。 比较结果：0(=)，1(>)，-1(<)
         */
        BigDecimal originalAmount = orderInfo.getOriginalAmount();
        if(orderInfo.getJoinStatus() != null && orderInfo.getJoinStatus().intValue() == PhoneConstants.JOIN_STATUS){
            originalAmount = originalAmount.subtract(BigDecimal.valueOf(1));
        }
        BigDecimal usedMoney = BigDecimal.valueOf(Long.parseLong(orderCoupon.getMoney()));
        int compResult = originalAmount.compareTo(BigDecimal.valueOf(Double.parseDouble(orderCoupon.getMoney())));
        if(compResult == -1){
            usedMoney = originalAmount;
        }
        
      //实际使用优惠券金额
        long realUseMoney =  usedMoney.multiply(new BigDecimal (100)).longValue();
        useCoupons.setRealUsedMoney(realUseMoney); 

        lstUseCoupons.add(useCoupons);
        return lstUseCoupons;
    }
}
