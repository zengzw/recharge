/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo;

import java.io.Serializable;

/**
 *
 * @author zengzw
 * @date 2017年5月5日
 */
public class RespReversalInfoVo  implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 外部订单号
     */
    private String orderNo;
    
    /**
     * 供应商订单号
     */
    private String spOrderNO;

    /**
     * 冲正时间
     */
    private String reversalTime;
    
    /**
     * 充值状态
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getReversalTime() {
        return reversalTime;
    }

    public String getSpOrderNO() {
        return spOrderNO;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setReversalTime(String reversalTime) {
        this.reversalTime = reversalTime;
    }
    
    public void setSpOrderNO(String spOrderNO) {
        this.spOrderNO = spOrderNO;
    }

}
