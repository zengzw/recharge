/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.enums;

/**
 * 订单类型
 * 
 * 
 * @author zengzw
 * @date 2017年3月7日
 */
public enum EnumRequestOrderType {

    TSH("淘实惠"),
    
    
    GX("顾乡");
    
    
    
    public static EnumRequestOrderType getEnume(String name) {
        for (EnumRequestOrderType item : values ()) {
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
     * 
     */
    private EnumRequestOrderType(String name) {
        this.typeName = name;
    }
    
    
  

    public String getTypeName() {
        return typeName;
    }
}
