package com.traintickets.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/22 022.
 */
public enum LogType {

    HTTP(1, "http日志"),
    DUBBO(2, "dubbo日志"),
    MQ(3, "mq日志"),
    APPLICATION(4, "应用日志");

    private Integer code;
    private String msg;

    private LogType(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private static final Map<Integer, ResponseCode> lookup = new HashMap<Integer, ResponseCode>();
    static
    {
        for (ResponseCode s : EnumSet.allOf(ResponseCode.class))
            lookup.put(s.getCode(), s);
    }

    public static ResponseCode get(Integer code)
    {
        return lookup.get(code);
    }
}
