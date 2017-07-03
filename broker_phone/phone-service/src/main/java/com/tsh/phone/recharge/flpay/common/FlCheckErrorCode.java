package com.tsh.phone.recharge.flpay.common;

/**
 * Created by Administrator on 2017/4/18 018.
 */
public enum FlCheckErrorCode {

    QUERY_ERROR("1206"),
    ORDER_ERROR("2115"),
    TIME_OUT("2407");

    private FlCheckErrorCode(String code){
        this.code = code;
    }

    private String code;

    public String getCode(){
        return this.code;
    }

}
