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
public enum EnumActivetyRewardStatus {

    DEFAULT(0,"待开奖"),


    
    PASS(1,"中奖"),


    NOT_PASS(2,"未中奖");



    private int type;


    private String desc;

    /**
     * 
     */
    EnumActivetyRewardStatus(int refundType,String name) {
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
