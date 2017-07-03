/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

/**
 * 退票请求参数
 * 
 * @author zengzw
 * @date 2016年12月2日
 */
public class RequestReturnTicketParam extends BaseRequestParam{

    /**
     * 退票订单号
     */
    private String orderNo;
    
    /**
     * 手机号码
     */
    private String mobile;
    
    /**
     * 验证码
     */
    private String code;

    public String getCode() {
        return code;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
