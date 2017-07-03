package com.tsh.vas.trainticket.commoms.enums;

/**
 * 订单状态
 *
 * @author zengzw
 * @date 2016年12月6日
 */
public enum EnumOrderInfoPayStatus {
    /**
     * 1：待支付
     */
    NON_PAYMENT ("待支付", 1, "等待结果"),
    
    /**
     * 2：支付中
     */
    PAIDING ("支付中", 2, "等待结果"),
    
    /**
     * 3：支付成功
     */
    PAY_SUCCESS ("支付成功", 3, "等待结果"),
    
    /**
     * 4：出票中
     */
    TRADING ("出票中", 4, "等待结果"),
    
    /**
     * 5：交易成功
     */
    TRAD_SUCCESS ("交易成功", 5, "购票成功"),
    
    /**
     * 6：支付失败
     */
    PAY_FAIL ("支付失败", 6, "扣款失败"),
    
    /**
     * 7：购票失败
     */
    TRAIN_FAIL ("购票失败", 7, "购票失败"),
    

    /**
     * 8：出票失败
     */
    TICKET_FAIL ("出票失败", 8, "购票失败"),
    

    /**
     * 9：结算中
     */
    SETTLEING  ("结算中", 9, ""),

    /**
     * 10:结算成功
     */
    SETTLE_SUCCESS ("结算成功", 10, ""),

    /**
     *  结算失败
     */
    SETTLE_FAIL ("结算失败", 11, ""),

    /**
     * 退款中
     */
    RETURNING ("退款中", 12, ""),

    /**
     * 退款成功
     */
    RETURN_SUCCESS("退款成功", 13, ""),


    /**
     * 退款失败
     */
    RETURN_FAIL("退款失败", 15, ""),

    /**
     * 关闭
     */
    ORDER_CLOSE ("关闭", 14, ""),
    
    /**
     * 支付异常 
     */
    PAY_EXCEPTION("支付异常", 16, "购票失败");

    private String desc;
    private Integer code;
    private String clientDesc;

    EnumOrderInfoPayStatus(String desc, Integer code, String clientDesc) {
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

    public static EnumOrderInfoPayStatus getEnume(Integer code) {
        for (EnumOrderInfoPayStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
