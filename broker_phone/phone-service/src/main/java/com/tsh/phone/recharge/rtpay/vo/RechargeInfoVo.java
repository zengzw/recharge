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
public class RechargeInfoVo {

    private String reqStreamId;

    private String orderNo;

    private Double balance;

    private String applyTime;

    private Double chargeNumBalance;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Double getChargeNumBalance() {
        return chargeNumBalance;
    }

    public void setChargeNumBalance(Double chargeNumBalance) {
        this.chargeNumBalance = chargeNumBalance;
    }

}
