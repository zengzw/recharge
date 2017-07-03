/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo.recharge;

/**
 *
 * @author zengzw
 * @date 2017年2月22日
 */
public class RequestCallbackVo {

    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 错误消息
     */
    private String message;
    
    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 合作商家订单号
     */
    private String customerOrderNo;

    /**
     * 状态
     */
    private String statusStr;


    public String getMessage() {
        return message;
    }



    public String getOrderId() {
        return orderId;
    }



    public Integer getStatus() {
        return status;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
