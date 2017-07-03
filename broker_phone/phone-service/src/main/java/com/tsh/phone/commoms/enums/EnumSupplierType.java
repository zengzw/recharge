/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.enums;


/** 
 * 供应商类型枚举
 * 
 * @author zengzw
 * @date 2017年2月20日
 */
public enum EnumSupplierType {

    OF("欧飞"),

    GY("高阳"),

    FL("福禄");


    private EnumSupplierType(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return this.value;
    }
    
    
    public static EnumSupplierType getByName(String name){
        for(EnumSupplierType supplierType : EnumSupplierType.values()){
            if(supplierType.name().equals(name)){
                return supplierType;
            }
        }
        
        return null;
    }
}
