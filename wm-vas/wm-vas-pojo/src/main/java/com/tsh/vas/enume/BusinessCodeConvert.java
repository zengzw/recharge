package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/10/11.
 */
public enum BusinessCodeConvert {
    DJSF ("缴水费", 1),
    DJDF ("缴电费", 2),
    JMQF ("缴煤气费", 3);
    private String desc;
    private Integer code;

    BusinessCodeConvert(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static BusinessCodeConvert getEnume(Integer code) {
        for (BusinessCodeConvert item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
