/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.manager;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.dubbo.hlq.enumType.CouponStatusEnum;
import com.tsh.dubbo.hlq.vo.vas.UseCouponDTO;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.utils.PhoneUtils;
import com.tsh.vas.iservice.IPayService;
import com.tsh.vas.model.ModuleAuthorityPo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.phone.enums.EnumActivetyRewardStatus;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.service.ModuleAuthorityService;
import com.tsh.vas.phone.service.PhoneUseCouponRecordService;
import com.tsh.vas.phone.service.VasPhoneOneyuanFreeService;
import com.tsh.vas.service.CouponService;
import com.tsh.vas.vo.coupon.QueryCouponParams;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;
import com.tsh.vas.vo.phone.api.APISuccessOrderInfo;

/**
 *  
 * @author zengzw
 * @date 2017年5月26日
 */
@Service
public abstract class AbstOrderInfoManagerService implements IPayService{

    private final static Logger logger = LoggerFactory.getLogger(APIAppPhoneService.class);

    /**
     * 活动权限设置
     */
    @Autowired
    protected ModuleAuthorityService authorityService;

    /**
     * 活动服务
     * 
     */
    @Autowired
    protected VasPhoneOneyuanFreeService activityService;


    /**
     * 优惠券服务接口
     */
    @Autowired
    CouponService couponService;


    @Autowired
    PhoneUseCouponRecordService phoneUseCouponRecordService;


    /**
     * 构建订单
     * 
     * @param result
     * @param phoneOrderInfoVo
     * @return
     * @throws Exception
     */
    public abstract PhoneOrderInfoPo createOrder(Result result,PhoneOrderInfoVo phoneOrderInfoVo) throws Exception;




    /**
     * 构建屏端返回结果
     */
    public abstract APISuccessOrderInfo buildResult(PhoneOrderInfoPo orderInfo);


    /**
     * 使用优惠券
     * 
     * @param orderInfoPo
     * @return
     * @throws FunctionException 
     */
    public PhoneUseCouponRecordPo applyCoupon(Result result,PhoneOrderInfoPo orderInfo,Integer couponId){
        QueryCouponParams queryParams = new QueryCouponParams();
        queryParams.setCouponIds(new Integer[]{couponId});
        queryParams.setOrderNo(orderInfo.getPhoneOrderCode());
        queryParams.setMoney(orderInfo.getSaleAmount().doubleValue());
        queryParams.setShopId(orderInfo.getBizId());
        queryParams.setUserId(Integer.parseInt(orderInfo.getPhoneMemberId()));
        List<UseCouponDTO> couponVOs =  couponService.applyCoupon(result, queryParams);
        
        UseCouponDTO couponVO = couponVOs.get(0);
        logger.info("#-->get list coupons result:{}",JSON.toJSONString(couponVO));
        
        //添加本地关联记录
        PhoneUseCouponRecordPo phoneUseCouponRecordPo = new PhoneUseCouponRecordPo();
        phoneUseCouponRecordPo.setCreateTime(new Date());
        phoneUseCouponRecordPo.setUpdateTime(new Date());
        phoneUseCouponRecordPo.setMerchantUserId(couponVO.getMerchantUserId().toString());
        phoneUseCouponRecordPo.setRecordId(couponVO.getCouponId().toString());
        phoneUseCouponRecordPo.setRuleId(couponVO.getCouponRuleId().toString());
        phoneUseCouponRecordPo.setCouponType(couponVO.getCouponType().toString());
        phoneUseCouponRecordPo.setMoney(couponVO.getCouponMoney().toString());
        phoneUseCouponRecordPo.setOrderCode(orderInfo.getPhoneOrderCode());
        phoneUseCouponRecordService.add(result, phoneUseCouponRecordPo);
        
        logger.info("#--->add phone coupon recode:{}",JSON.toJSONString(phoneUseCouponRecordPo));
        return phoneUseCouponRecordPo;
    }
    
    
    /**
     *  修改优惠券状态
     *  
     * @param result
     * @param couponId
     * @throws Exception 
     */
    public void updateCouponStatus(Result result,Integer couponId,CouponStatusEnum status) throws  Exception{
        couponService.updateCouponStatusById(result, Long.parseLong(couponId.toString()), status);
    }



    /**
     * 活动添加
     * 
     * @param orderinfo
     */
    protected  void addActivity(Result result,PhoneOrderInfoPo phoneOrderInfoPo) throws FunctionException{
        //判断活动是否到期
        ModuleAuthorityPo isExpire =  authorityService.queryPhoneActivityByAreaId(result, phoneOrderInfoPo.getBizId()).getData();
        if(isExpire == null){
            throw new FunctionException(result, "对不起，话费充值活动已经结束");
        }

        //保存活动信息
        VasPhoneOneyuanFreeVo activety = new VasPhoneOneyuanFreeVo();
        activety.setOrderCode(phoneOrderInfoPo.getPhoneOrderCode());
        activety.setChargeAmount(phoneOrderInfoPo.getSaleAmount());
        activety.setChargeMobile(phoneOrderInfoPo.getRechargePhone());
        activety.setActivityAmount(phoneOrderInfoPo.getActivetyMoeny());
        activety.setCreateTime(new Date());
        activety.setCheckStatus(EnumActivetyAuditStatus.DEFAULT.getType());
        activety.setLotteryStatus(EnumActivetyRewardStatus.DEFAULT.getType());
        activety.setBizType(EnumBusinessCode.MPCZ.getBusinessName());
        activety.setAreaId(Integer.parseInt(phoneOrderInfoPo.getCountryCode()));
        activety.setAreaName(phoneOrderInfoPo.getCountryName());
        activety.setStoreId(Integer.parseInt(phoneOrderInfoPo.getStoreCode()));
        activety.setStoreName(phoneOrderInfoPo.getStoreName());

        VasPhoneOneyuanFreePo vasPhone =  activityService.addVasPhoneOneyuanFree(result, activety).getData();

        logger.info("-> 添加参与活动对象：{}",JSON.toJSONString(activety));

        //修改幸运号
        activityService.updateLuckNumById(result,vasPhone.getId(),PhoneUtils.generateLuckNumber(vasPhone.getId()));
    }

}
