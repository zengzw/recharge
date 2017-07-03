/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dtds.platform.util.bean.Result;
import com.tsh.dubbo.hlq.enumType.CouponStatusEnum;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;

/**
 *  订单管理策略类
 *  
 * @author zengzw
 * @date 2017年5月26日
 */
public class OrderInfoMangerStrategy{

    private final static Logger logger = LoggerFactory.getLogger(OrderInfoMangerStrategy.class);


    private AbstOrderInfoManagerService stategy;


    public OrderInfoMangerStrategy(AbstOrderInfoManagerService service) {
        this.stategy = service;
    }



    public Result operateion(Result result,PhoneOrderInfoVo orderInfoVo) throws Exception{
        boolean isUseCoupon = false;
        try{
            //添加订单
            PhoneOrderInfoPo orderInfo = stategy.createOrder(result, orderInfoVo);
            logger.info("#-->【2】话费添加订单成功：{}",orderInfo.getPhoneOrderCode());

            //参加活动
            if(orderInfoVo.getJoinStatus() != null 
                    && orderInfoVo.getJoinStatus().intValue() == PhoneConstants.JOIN_STATUS){
                stategy.addActivity(result, orderInfo);
                logger.info("#-->话费订单号:{}添加【一元免单】订单",orderInfo.getPhoneOrderCode());
            }

            //使用代金券
            if(orderInfoVo.getCouponId() != null && orderInfoVo.getCouponId() > 0){
                isUseCoupon = true;
                PhoneUseCouponRecordPo orderCoupon =  stategy.applyCoupon(result, orderInfo,orderInfoVo.getCouponId());
                orderInfo.setOrderCoupon(orderCoupon);
                logger.info("#-->话费订单号:{}使用【优惠券】，优惠券Id:{}",orderInfo.getPhoneOrderCode(),orderCoupon.getRecordId());
            }
            
            //支付
            stategy.pay(result, orderInfo);


        }catch(Exception e){
            //下单异常,如果使用了优惠券,要进行解绑,再把异常往上抛。这里使用dubbo接口，没有涉及到分布式事务.
            if(isUseCoupon){
                logger.info("#--> 解绑代金券：couponId:{},status:{}",orderInfoVo.getCouponId(),CouponStatusEnum.NOT_USE.getStatus());
                stategy.updateCouponStatus(result, orderInfoVo.getCouponId(), CouponStatusEnum.NOT_USE);
            }

            throw e;
        }

        return result;

    }

}
