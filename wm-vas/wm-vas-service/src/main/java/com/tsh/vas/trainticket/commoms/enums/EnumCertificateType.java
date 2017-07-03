/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.commoms.enums;

/**
 * 乘客证件类型
 * 
 * @author zengzw
 * @date 2016年11月25日
 */
public enum EnumCertificateType {

    ID_Card(1,"一代身份证"),

    ID_Card2(2,"二代身份证"),

    ID_HK(3,"港澳通行证"),

    ID_TW(4,"台湾通行证"),

    ID_Passport(5,"护照");

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    EnumCertificateType(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
