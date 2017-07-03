package com.tsh.vas.commoms.enums;

/**
 * Created by Administrator on 2017/4/17 017.
 */
public enum ModifyOrderStatus {

    SUCCESS("交易成功"),
    
    CHARGE_FAIL("交易异常"),
    
    FAILURE("交易失败"),

    PHONE_CHARGING("话费充值中"),

    NOT_TIME("不在操作时间内"),

    REQUEST_ERROR("请求失败"),

    UNKNLOW_STATUS("未知状态");

    ModifyOrderStatus(String  desc){
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
