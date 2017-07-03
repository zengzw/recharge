package com.tsh.vas.phone.enums;

/**
 * 订单状态
 *
 * @author zengzw
 * @date 2016年12月6日
 */
public enum EnumPhoneOrderInfoPayStatus {
    /**
     * 1：待支付
     */
    NON_PAYMENT ("待支付", 1, "待支付"),
    
    
    /**
     * 2：支付中    
     */
    PAIDING ("支付中", 2, "支付中"),
    
    
    /**
     * 3：支付成功
     */
    PAY_SUCCESS ("支付成功", 3, "支付成功"),
    
    
    /**
     * 4：充值中
     */
    TRADING ("充值中", 4, "充值中"),
    
    
    
    /**
     * 5：交易成功
     */
    TRAD_SUCCESS ("交易成功", 5, "充值成功"),
    
    
    /**
     * 6：支付失败
     */
    PAY_FAIL ("支付失败", 6, "扣款失败"),
    
    
    /**
     * 7：充值失败
     */
    TRAIN_FAIL ("充值失败", 7, "充值失败"),
    

    /**
     * 支付异常 
     */
    PAY_EXCEPTION("支付异常", 16, "购票失败"),

    UNKNOWN("未知状态", 99, "未知状态");

    private String desc;
    private Integer code;
    private String clientDesc;

    EnumPhoneOrderInfoPayStatus(String desc, Integer code, String clientDesc) {
        this.desc = desc;
        this.code = code;
        this.clientDesc = clientDesc;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public static EnumPhoneOrderInfoPayStatus getEnume(Integer code) {
        for (EnumPhoneOrderInfoPayStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        return UNKNOWN;
    }
}
