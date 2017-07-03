package com.traintickets.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/30 030.
 */
public enum TrainType {
    G("G", "G-高铁"),
    D("D", "D-动车"),
    Z("Z", "Z-直达"),
    T("T", "T-特快"),
    K("K", "K-快速"),
    C("C", "C-城际"),
    Q("Q", "Q-其他");


    private String code;
    private String desc;

    private TrainType(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private static final Map<String, TrainType> lookup = new HashMap<String, TrainType>();
    static
    {
        for (TrainType s : EnumSet.allOf(TrainType.class))
            lookup.put(s.getCode(), s);
    }

    public static TrainType get(String code)
    {
        return lookup.get(code);
    }
}
