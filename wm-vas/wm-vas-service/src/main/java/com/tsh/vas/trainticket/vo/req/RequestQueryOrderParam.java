/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

/**
 * 获取   订单详情   请求参数 实体对象
 * 
 * @author zengzw
 * @date 2016年11月24日
 */
public class RequestQueryOrderParam extends BaseRequestParam {
    /**
     * 外部订单Id
     */
    private String merchantOrderId;

    /**
     * 供应商订单Id
     */
    private String orderId;

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
