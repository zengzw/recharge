/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

/**
 * 手机验证请求实体
 * 
 * @author zengzw
 * @date 2016年11月25日
 */
public class RequestValidateSmsCodeParam {

    /**
     * 手机号码
     */
    private String mobile;
    
    
    /**
     * 验证码
     */
    private String code;


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }
}
