/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constants;

/**
 * 短信内容
 * 
 * @author zengzw
 * @date 2016年12月3日
 */
public class SMSPhoneMessageTemplateConstants {
    
    
    private SMSPhoneMessageTemplateConstants(){};
    
    
    


    /**
     * 支付失败
     * 
     * 充值手机，订单号
     */
    public static String PAY_FAIL = "亲爱的%s，很抱歉，您的话费充值订单（%s）支付账户扣款失败了，未能进行话费的充值。";


    
    
  
    /**
     * 充值失败
     * 
     *  充值手机，订单号，金额
     */
    public static String TRAIN_FAIL = "亲爱的%s，很抱歉，您的话费充值订单（%s）充值失败了，将退款%s元，退款金额将返还至支付账户。";





    /**
     * 退款失败
     * 
     *  会员手机，订单号、退款金额
     */
    public static String RETURN_AMONUT_FAIL = "亲爱的%s，很抱歉，话费充值订单（%s）充值退款失败了，退款金额%s元，我们将为您进行人工处理进行退款。";



    /**
     * 一元免单中奖短(中奖用户)
     * 充值手机号，充值金额，订单编号
     */
    public static final String LOTTERY_MESSAGE_USER = "恭喜%s，您参与话费1元活动获得了%s元奖励，请到充值小店话费活动页面进行核对后兑换，充值订单号%s。";



    /**
     * 一元免单中奖短信(网点老板)
     * 充值手机号，充值金额，订单编号
     */
    public static final String LOTTERY_MESSAGE_BOSS = "恭喜您的网点客户%s参与话费营销活动获得了%s元奖励，订单号%s，请配合客户前来领取。";


    /**
     * 代金券发短信给用户
     */
    public static final String LOTTERY_CASH_COUPON_USER = "恭喜%s，您参与话费1元活动获得了%s（使用时间：%s），请到充值小店话费活动页面中核对，充值订单号%s。";
    
    /**
     * 网点老板
     */
    public static final String LOTTERY_CASH_COUPON_STORE = "恭喜您的小店客户%s参与话费营销活动获得了%s，订单号%s。";

}
