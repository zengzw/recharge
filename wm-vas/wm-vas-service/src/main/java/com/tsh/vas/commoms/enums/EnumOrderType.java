/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.enums;

/**
 *
 * @author zengzw
 * @date 2017年4月14日
 */
public enum EnumOrderType {

    HCP("火车票",3),

    DJDF("缴电费",2),

    MPCZ("话费",1);

    /**
     * 
     */
    private EnumOrderType(String businessName,int code) {
        this.businessName = businessName;
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    private String businessName;
    
    private int code;

    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    
    public static EnumOrderType getByName(String name){
        for(EnumOrderType e :EnumOrderType.values()){
            if(e.name().equals(name)){
                return e;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println(EnumOrderType.getByName("HC1P").getBusinessName());
    }
}
