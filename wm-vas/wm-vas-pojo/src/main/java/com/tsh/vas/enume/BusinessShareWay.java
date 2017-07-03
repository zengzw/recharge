package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum BusinessShareWay {
    DISABLE ("可分成", 1),

    FULL_AMOUNT ("全额分成", 2),

    MARGIN_AMOUNT ("差额分成", 3);

    private String desc;
    private Integer code;

    BusinessShareWay(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static BusinessShareWay getEnume(Integer code) {
        for (BusinessShareWay item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
