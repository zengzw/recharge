/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.vo;

import com.alibaba.fastjson.JSON;

/**
 * 对象转换
 * 
 * @author zengzw
 * @date 2017年5月2日
 */
public class OrderInfoVo{
    
    /**
     * 外部订单号
     */
    private String reqStreamId;

    /**
     * 供应商订单号
     */
    private String orderNo;

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
