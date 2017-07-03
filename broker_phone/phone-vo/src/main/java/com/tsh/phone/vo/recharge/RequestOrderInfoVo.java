/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo.recharge;

/**
 *  订单查询VO
 *
 * @author zengzw
 * @date 2017年2月16日
 */
public class RequestOrderInfoVo extends BaseRequestVo{
    
    
    /**
     * 订单Id
     */
    private String orderId;
    
   


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


}
