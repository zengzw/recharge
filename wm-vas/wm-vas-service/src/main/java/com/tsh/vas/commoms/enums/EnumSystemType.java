/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.commoms.enums;

/**
 *
 * @author zengzw
 * @date 2017年6月22日
 */
public enum EnumSystemType {

    B2B(1),
    
    B2C(2);
    
    private int code;
    
    EnumSystemType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}
