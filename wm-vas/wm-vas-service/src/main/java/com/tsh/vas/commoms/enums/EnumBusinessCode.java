/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.enums;

/**
 * 
 *  业务编码
 * 
 * @author zengzw
 * @date 2017年3月2日
 */
public enum EnumBusinessCode {

    HCP("火车票"),

    DJDF("水电煤"),

    MPCZ("话费");


    EnumBusinessCode(String  businessName){
        this.businessName = businessName;
    }

    private String businessName;

    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    
    public static EnumBusinessCode getBusinessCode(String name){
        for(EnumBusinessCode code:EnumBusinessCode.values()){
            if(name.equals(code.name())){
                return code;
            }
        }
        
        return null;
    }
}
