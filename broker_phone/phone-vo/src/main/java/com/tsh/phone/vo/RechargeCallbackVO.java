/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo;

/**
 * 充值回调VO
 * 
 * @author zengzw
 * @date 2017年2月22日
 */
public class RechargeCallbackVO {

    /**
     * 状态
     */
    private int status;
    
    /**
     * 订单ID
     */
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
