/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.constants;

/**
 * 短信内容
 * 
 * @author zengzw
 * @date 2016年12月3日
 */
public class SMSMessageTemplateConstants {
    
    
    private SMSMessageTemplateConstants(){};
    
    /**
     * 开车时间前1天12:00进行一次发送
     */
    public static String REMIND = "您购买的火车票%s%s就要开车了，由%s开往%s的%s次列车（%车%s%s），如还未到火车站换取纸质车票的话别忘了提前去换取后再乘车哦。";

    /**
     * 购票成功
     *  会员手机，订单号
     */
    public static String TRAIN_SUCCESS = "亲爱的%s，您的火车票订单（%s）已购票成功，%s %s由%s开往%s的%s次列车（%s车%s%s），取票号：%s，请在开车时间前及时到火车站换取车票。";

    
    
    /**
     * 购票失败
     * 、 会员手机，订单号
     */
    public static String TRAIN_FAIL = "亲爱的%s，很抱歉，您的火车票订单（%s）购票失败了，2小时后我们将为您进行退款。";




    /**
     * 支付失败
     * 会员手机，订单号
     */
    public static String PAY_FAIL = "亲爱的%s，很抱歉，您的火车票订单（%s）未支付成功。";





    /**
     * 退票成功
     *  会员手机，订单号，金额
     */
    public static String RETURN_TICKET_SUCCESS = "亲爱的%s，您的火车票订单（%s）已退票成功，6小时后进行退款处理，退款金额：%s元。";





    /**
     * 退票失败
     * 
     *  会员手机，订单号
     */
    public static String RETURN_TICKET_FAIL = "亲爱的%s，您的火车票订单（%s）退票申请失败了。";




    /**
     * 退款成功
     * 
     *  会员手机，订单号，金额
     */
    public static String RETURN_AMONUT_SUCCESS = "亲爱的%s，您的火车票订单（%s）已退款成功至支付账户，退款金额：%s元。";




    /**
     * 退款失败
     * 
     *  会员手机，订单号
     */
    public static String RETURN_AMONUT_FAIL = "亲爱的%s，您的火车票订单（%s）退款失败，我们将进行人工退款。";
    
    
    /**
     * 退票
     */
    public static String RETURN_TICKET = "亲爱的%s，您正在申请火车票退票申请，申请验证码为:%s";
    

}
