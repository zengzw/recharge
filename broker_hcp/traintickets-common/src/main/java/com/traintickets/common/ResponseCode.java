package com.traintickets.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {

    SYSTEM_ERROR(100, "系统异常"),
	SUCCESS(200, "处理成功"),
    ENCRYPT_ERROR(201, "参数加密异常"),
    ISO88591_ERROR(202, "iso8859-1转码异常"),
    PARAM_NOT_EMPTY(203, "参数不能为空"),
    CODING_CHANGE_ERROR(204, "iso转utf8异常"),
    NOT_SUPPORT_ERROR(205, "不支持该类型方法,GET/POST"),

    QUERY_TICKETS_ERROR(301, "查询车次列表异常"),
    QUERY_CHECKT_NUM_ERROR(302, "查询车次余票数量异常"),
    VERIFY_USERS_ERROR(303, "验证用户信息异常"),
    QUERY_SUBWAY_STATION_ERROR(304, "查询车次途径站点列表异常"),
    QUERY_ORDER_ERROR(305, "查询订单信息异常"),
    CREATE_ORDER_ERROR(306, "创建购票订单异常"),
    REFUND_TICKET_ERROR(307, "退票退款异常"),

    VERIFY_USERS_FAIL(308, "验证用户信息失败"),

    CANNOT_REFUND(500, "供应商返回不能退票");

    private Integer code;
    private String msg;

    private ResponseCode(Integer code, String msg)
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
