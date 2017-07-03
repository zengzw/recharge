/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.properties;

/**
 * properties key 常量
 * 
 *  定义此key的话，是为了应对高阳的错误编码不是唯一导致的问题。
 *  
 *  不同业务的的接口，返回错误码是一样的。所以我们内部要做下区分。
 * 
 * 
 * @author zengzw
 * @date 2017年2月27日
 */
public class GYPropertiesKey {
    
    /**
     * 充值
     */
    public static String  RECHARAGE = "order.1000";
    
    
    
    /**
     * 产品查询
     */
    public static String  QUERY_PRODUCT = "product.1000";
    
    
    
    /**
     * 订单查询
     */
    public static String  QUERY_ORDER = "order.query.1000";
    
    
    
    /**
     * 号码段查询
     * 
     */
    public static String  SEGMENT = "segment.1000";
    
    
    
}
