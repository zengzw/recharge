package com.tsh.vas.trainticket.commoms.enums;

/**
 * Created by Administrator on 2016/12/6 006.
 */
public enum EnumOrderListType {

    WAIT("1", "等待结果"),

    SUCCESS("2", "已成功"),

    FAIL("3", "已失败"),

    REFUND("4", "已退票");

    private String type;

    private String msg;

    EnumOrderListType(String type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

}
