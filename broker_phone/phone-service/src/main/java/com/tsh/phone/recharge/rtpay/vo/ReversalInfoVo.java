/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.vo;

/**
 *
 * @author zengzw
 * @date 2017年5月2日
 */
public class ReversalInfoVo {

    /**
     * 外部订单号
     */
    private String reqStreamId;

    /**
     *  瑞通订单号
     */
    private String orderNo;


    /**
     * 冲正时间
     */
    private String reversalTime;


    public String getReversalTime() {
        return reversalTime;
    }

    public void setReversalTime(String reversalTime) {
        this.reversalTime = reversalTime;
    }

    public String getReqStreamId() {
        return reqStreamId;
    }

    public void setReqStreamId(String reqStreamId) {
        this.reqStreamId = reqStreamId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


}
