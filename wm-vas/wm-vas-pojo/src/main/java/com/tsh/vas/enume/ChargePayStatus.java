package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum ChargePayStatus {
    /**
     * 1：待支付
     */
    NON_PAYMENT ("待支付", 1, "充值中"),
    /**
     * 2：支付中
     */
    PAIDING ("支付中", 2, "充值中"),
    /**
     * 3：缴费中
     */
    CHARGING ("缴费中", 3, "充值中"),
    /**
     * 4：交易成功
     */
    TRAD_SUCCESS ("交易成功", 4, "交易成功"),
    /**
     * 5：交易失败
     */
    TRAD_FAIL ("交易失败", 5, "交易失败"),
    /**
     * 6：交易关闭
     */
    TRAD_CLOSE ("交易关闭", 6, "交易失败"),
    /**
     * 7：支付异常
     */
    PAY_FAIL ("支付异常", 7, "交易失败"),
    /**
     * 8：缴费异常
     */
    CHARGE_FAIL ("缴费异常", 8, "交易失败"),
    /**
     * 9：撤销
     */
    REVOCATION ("撤销", 9, "撤销"),
    /**
     * 10:订单扣款成功
     */
    PAY_SUCCESS ("扣款成功", 10, "充值中");

    private String desc;
    private Integer code;
    private String clientDesc;

    ChargePayStatus(String desc, Integer code, String clientDesc) {
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

    public static ChargePayStatus getEnume(Integer code) {
        for (ChargePayStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
