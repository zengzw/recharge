/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo;

/**
 *
 * @author zengzw
 * @date 2017年4月14日
 */
public class QueryOrderChangeVO {
    
    private String orderId;
    
    private String orderType;
    
    
    private String orderNo;
    
    private String payBalance;
    
    private String orderStatus;
    
    private String refundStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getPayBalance() {
        return payBalance;
    }

    public void setPayBalance(String payBalance) {
        this.payBalance = payBalance;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }
    

}
