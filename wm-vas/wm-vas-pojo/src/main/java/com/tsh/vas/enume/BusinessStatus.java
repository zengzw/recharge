package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum BusinessStatus {
    ABLE ("可服务", 1),

    DISABLE ("不可服务", 2);

    private String desc;
    private Integer code;

    BusinessStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static BusinessStatus getEnume(Integer code) {
        for (BusinessStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
