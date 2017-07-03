/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.enums;

/**
 * 退款
 * 
 * @author zengzw
 * @date 2016年12月1日
 */
public enum EnumPhoneRefundType {
    
    /**
     * 退款
     */
    RECHARGE_ERROR(1,"充值失败"),
    
    
    /**
     * 支付异常（没有回调）
     */
    PAY_EXCEPTION(1,"支付异常");
    
    
    
    private int type;
    

    private String name;

    /**
     * 
     */
    EnumPhoneRefundType(int refundType,String name) {
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
