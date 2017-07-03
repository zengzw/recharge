package com.tsh.broker.enumType;

/**
 * LogTypeEnum
 *
 * @author dengjd
 * @date 2016/10/19
 */
public enum  LogTypeEnum {
    DEFAULT(0,"DEFAULT"),
    LOG_HTTP(1,"HTTP"),
    LOG_DUBBO(2,"DUBBO"),
    LOG_MQ(3,"MQ"),
    LOG_FUN(4,"FUNCTION");

    private int value;
    private String name;

    private LogTypeEnum(int value, String name){
        this.value = value;
        this.name = name;
    }

    public static LogTypeEnum valueOf(int value){
        for (LogTypeEnum bizzTypeEnum : LogTypeEnum.values()) {
            if (bizzTypeEnum.value == value) {
                return bizzTypeEnum;
            }
        }

        return DEFAULT;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
