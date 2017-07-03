package com.tsh.vas.netgold.common.enums;


/**
 * Created by Administrator on 2017/4/11 011.
 */
public enum  NetgoldOrderStatus {

    WAITING(1, "待退款"),

    PAY_SUCCESS(2, "扣款成功"),

    TRAD_SUCCESS(3, "交易成功成功"),

    OTHER(9, "其他");

    private Integer code;
    private String clientDesc;

    NetgoldOrderStatus(Integer code, String clientDesc) {
        this.code = code;
        this.clientDesc = clientDesc;
    }

    public Integer getCode() {
        return code;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public static NetgoldOrderStatus getEnume(Integer code) {
        for (NetgoldOrderStatus item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("请求参数非法");
    }
}
