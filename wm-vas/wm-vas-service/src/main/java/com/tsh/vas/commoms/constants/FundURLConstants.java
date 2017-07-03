/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.constants;

/**
 * 账户资金URL常量量
 * 
 * 
 * @author zengzw
 * @date 2017年3月8日
 */
public class FundURLConstants {
    
    private FundURLConstants(){};
    
    
    /**
     * 查询订单状态
     */
    public static final String ACC_QUERY_ORDER_STATUS= "api/orderPay/queryOrderStatus.do";
    
    
    /**
     * 获取会员信息
     */
    public static final String ACC_MEMEBER_INFO = "/member/app/getMemberUserInfo.do";
    
    
    /**
     *  领取优惠券获取会员信息（是会员就直接返回会员信息，不是就自动帮其号码注册，然后返回会员信息
     */
    public static final String ACC_MEMEBER_FOR_APP = "/member/app/getMemberInfoForApp.do";
    
    
    /**
     * 根据会员Id查找会员信息
     */
    public static final String ACC_MEMEBER_INFO_BY_ID = "/member/api/getMemberInfoByMemberId.do";


    
    
    /**
     * 校验账户支付密码
     */
    public static final String ACC_VALIDATE_OUTPAY_PASSWORD = "/accpwd/validateOutPayPwd.do";
    
    
    /**
     * 校验会员信息密码
     */
    public static final String ACC_VALIDATE_MEMBERCARD = "/member/app/validateMemberCard.do";
    
    
}
