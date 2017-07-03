/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.enums;

/**
 * 电话服务商枚举
 * 
 * 
 * @author zengzw
 * @date 2017年3月7日
 */
public enum EnumServiceProviceType {

    YDCZ("移动","中国移动"),
    
    
    LTCZ("联通","中国联通"),
    
    
    DXCZ("电信","中国电信");
    
    
    public static EnumServiceProviceType getEnume(String name) {
        for (EnumServiceProviceType item : values ()) {
            if (item.name().equals (name)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
    
    /**
     * 简称
     */
    private String typeName;

    /**
     * 全称
     */
    private String fullTypeName;


    /**
     * 
     */
    private EnumServiceProviceType(String name,String fullName) {
        this.typeName = name;
        this.fullTypeName = fullName;
    }
    
    
    public String getFullTypeName() {
        return fullTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setFullTypeName(String fullTypeName) {
        this.fullTypeName = fullTypeName;
    }
    
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
