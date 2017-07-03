/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.commoms.enums;

/**
 *
 * @author zengzw
 * @date 2016年12月1日
 */
public enum EnumRefundType {
    
    /**
     * 购票失败退款
     */
    BUY_TICKET_ERROR(1,"购票退款"),
    
    
    /**
     * 退票退款
     */
    RETURN_TICKET(2,"退票退款"),
    
    /**
     * 支付异常（没有回调）
     */
    PAY_EXCEPTION(3,"支付异常");
    
    
    
    private int type;
    

    private String name;

    /**
     * 
     */
    EnumRefundType(int refundType,String name) {
        this.type = refundType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setType(int type) {
        this.type = type;
    }
}
