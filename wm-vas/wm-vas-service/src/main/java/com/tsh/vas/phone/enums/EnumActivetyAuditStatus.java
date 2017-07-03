/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.enums;

/**
 *
 * @author zengzw
 * @date 2017年4月19日
 */
public enum EnumActivetyAuditStatus {

    
    DEFAULT(0,"待审核"),


    PASS(1,"审核通过"),


    NOT_PASS(2,"审核不通过");



    private int type;


    private String desc;

    /**
     * 
     */
    EnumActivetyAuditStatus(int refundType,String name) {
        this.type = refundType;
        this.desc = name;
    }


    public int getType() {
        return type;
    }


    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public void setType(int type) {
        this.type = type;
    }
}
