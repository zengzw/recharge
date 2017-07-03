package com.tsh.vas.enume;

/**
 * Contains code, message and data for an web API response.
 *
 * <p> Same general response codes and messages are defined as public constants in this class. </p>
 *
 * <p> <b>Thread Safety:</b> This class is immutable and is thread safe. </p>
 *
 * @author iritchie.ren
 * @version 1.0
 * @since 1.1
 */
public enum ResponseCode {
    OK_CODE ("OK", 2200, "请求成功"),
    DEFAULT_ERROR_CODE ("Failed", 2201, "请求失败"),
    ACCESS_DENIED_CODE ("Access Denied", 2202, "没有权限"),
    ENTITY_EXISTS_CODE ("Entity Already Exists", 2203, "记录已经存在"),
    INVALID_PARAM_CODE ("Invalid Param", 2204, "非法请求参数"),
    ENTITY_NOT_FOUND_CODE ("Entity Not Found", 2205, "记录不存在"),
    BALANCE_INSUFFICIENT_CODE ("Balance Insufficient", 2206, "余额不足"),
    DUPLICATE_DATA_CODE ("Duplicate Data", 2207, "已存在相同的数据"),
    REQUEST_EXCEPTION_CODE ("Request Exception", 2208, "请求异常"),
    REQUEST_EXCEPTION_PRICE ("Request Exception", 2209, "价格验证失败");

    private String desc;
    private Integer code;
    private String msg;

    ResponseCode(String desc, Integer code, String msg) {
        this.desc = desc;
        this.code = code;
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseCode getEnume(String code) {
        for (ResponseCode item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("ResponseCode枚举参数非法");
    }
}
