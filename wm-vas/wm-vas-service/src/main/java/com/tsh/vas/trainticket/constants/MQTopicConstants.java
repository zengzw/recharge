/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.constants;

/**
 * MQ 主题
 * @author zengzw
 * @date 2016年12月12日
 */
public class MQTopicConstants {

    private MQTopicConstants(){};
    
    /**
     * 支付回调
     */
    public static final String PAY_NOTIFY = "VAS_HCPPayTopic";
    
    
    /**
     * 退款回调
     */
    public static final String REFUND_NOTIFY="VAS_HCPRefoundTopic";
    
    
    /**
     * 确认结算MQ主题
     */
    public final static String MQ_CONFIRM_SETTLE = "FINA-ComfirmOrderSettleTopic";
}
