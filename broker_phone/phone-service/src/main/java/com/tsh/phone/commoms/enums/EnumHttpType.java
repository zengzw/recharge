/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.commoms.enums;


/**
 * HTTP 请求类型枚举
 * 
 * @author zengzw
 * @date 2017年2月20日
 */
public enum EnumHttpType {

    HTTP_GET("get"),

    HTTP_POST("post");


    private EnumHttpType(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return this.value;
    }
    
    public static EnumHttpType getByName(String name ){
        for(EnumHttpType supplierType : EnumHttpType.values()){
            if(supplierType.name().equals(name)){
                return supplierType;
            }
        }
        return null;
    }
  
}
