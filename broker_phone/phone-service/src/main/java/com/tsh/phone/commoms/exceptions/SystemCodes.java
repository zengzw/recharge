/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.exceptions;

import com.alibaba.fastjson.JSON;

/**
 * 错误码定义格式位 供应商 + 具体错误吗
 * 
 * @author zengzw
 * @date 2016年7月26日
 */
public class SystemCodes {
    
    public static final String GY = "GY";
    
    public static final String OF = "OF";
    
    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR = 911;
    
    /**
     *  网络异常
     */
    public static final int NETWORK_ERROR = 912;
    
    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 200;
    
    
    /**
     * 失败
     */
    public static final int ERROR_CODE = 500;
    
    
    
    public static void main(String[] args) {
        String name = "ddd";
        System.out.println(JSON.toJSONString(name));
    }
}
