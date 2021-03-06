package com.tsh.vas.phone.enums;

/**
 *  退款状态 
 */
public enum EnumPhoneRefundOrderStatus {
    /**
     * 3：待退款
     */
    NON_REFUND ("待退款", 3, "待退款"),
    
    /**
     * 12：退款中
     */
    REFUNDING ("退款中", 12, "退款中"),
    
    /**
     * 13：退款成功
     */
    REFUND_SUCCESS ("退款成功", 13, "退款成功"),
    
    /**
     * 15：退款失败
     */
    REFUND_FAIL ("退款失败", 15, "退款失败");

    private String desc;
    private Integer code;
    private String clientDesc;

    EnumPhoneRefundOrderStatus(String desc, Integer code, String clientDesc) {
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

    public static EnumPhoneRefundOrderStatus getEnume(Integer code) {
        for (EnumPhoneRefundOrderStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
