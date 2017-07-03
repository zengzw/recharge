package com.tsh.vas.trainticket.commoms.enums;

/**
 * Created by Administrator on 2016/12/9 009.
 */
public enum  EnumPayWay {

    STORE(4,"网点支付"),

    MEMBER(9,"会员支付");

    EnumPayWay(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

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

    public static EnumPayWay getEnume(Integer code) {
        if(null == code){
            return null;
        }
        for (EnumPayWay item : values ()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
