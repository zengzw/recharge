package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum DivideRule {
    /**
     * 1.全额
     */
    FULL_AMOUNT ("全额", 1),
    /**
     * 2.差额
     */
    MARGIN_AMOUNT ("差额", 2);

    private String desc;
    private Integer code;

    DivideRule(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static DivideRule getEnume(Integer code) {
        for (DivideRule item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
