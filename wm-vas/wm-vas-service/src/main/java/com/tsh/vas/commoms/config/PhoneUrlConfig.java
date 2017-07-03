/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.config;

/**
 * 话费地址配置
 * 
 * 
 * @author zengzw
 * @date 2017年3月6日
 */

public class PhoneUrlConfig {



    private PhoneUrlConfig(){};

    /**
     * 获取手机号码段
     */
    public static final String QUERY_PHONE_TYPE = "phonetype";


    /**
     * 查询话费订单
     */
    public static final String QUERY_ORDER_INFO = "orderinfo";


    /**
     * 充值
     */
    public static final String RECHARGE = "recharge";
    
    
    /**
     * 取消充值
     * 
     */
    public static final String REVERSAL = "reversal";


}
