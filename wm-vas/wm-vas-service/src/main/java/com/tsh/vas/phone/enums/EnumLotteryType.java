package com.tsh.vas.phone.enums;

/**
 * Created by Administrator on 2017/5/22 022.
 */
public enum EnumLotteryType {

    M("免单"),

    G("鼓励金"),

    D("代金券"),

    UNKNOWN("未知类型");

    private String name;

    EnumLotteryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public static EnumLotteryType getEnume(String code) {
        for (EnumLotteryType item : values ()) {
            if (item.toString().equals (code)) {
                return item;
            }
        }
        return UNKNOWN;
    }
}
