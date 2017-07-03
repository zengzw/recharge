package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum ChargeRefundStatus {
    /**
     * 0：未发生退款
     */
    NORMAL_REFUND ("未发生退款", 0, "-"),
    /**
     * 1：退款中
     */
    WAIT_REFUND ("待退款", 1, "退款中"),
    /**
     * 2：已退款
     */
    REFUND ("已退款", 2, "退款成功"),
    /**
     * 3：退款失败
     */
    REFUND_FAIL ("退款失败", 3, "退款失败");

    private String desc;
    private Integer code;
    private String clientDesc;

    ChargeRefundStatus(String desc, Integer code, String clientDesc) {
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

    public static ChargeRefundStatus getEnume(Integer code) {
        for (ChargeRefundStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
