package com.tsh.broker.enumType;

/**
 * Created by Iritchie.ren on 2016/10/23.
 */
public enum PayResultType {
    /**
     * 充值中
     */
    CHARGING ("充值中", 70001),
    /**
     * 充值成功
     */
    CHARGING_SUCCESS ("充值成功", 70002),
    /**
     * 充值失败
     */
    CHARGING_FAIL ("充值失败", 70003),
    /**
     * 充值结果未定
     */
    CHARGING_UNDEFINED ("充值结果未定", 70004);

    private String desc;
    private Integer code;

    PayResultType(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static PayResultType getEnume(Integer code) {
        for (PayResultType item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
