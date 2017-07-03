package com.tsh.phone.recharge.flpay.common;


/**
 * Created by Administrator on 2017/4/12 012.
 */
public enum FlConstantsEnums {

    QUERY_MOBILE("kamenwang.phonegoods.checkandgetprice"),

    QUERY_MOBILE_SUCCESS_CODE("2400"),

    QUERY_ORDER("kamenwang.order.get"),

    QUERY_ORDER_SUCCESS_CODE("成功"),

    CHARGE("kamenwang.phoneorder.add"),

    CHARGE_SUCCESS_STATUS("未处理"),

    CHARGE_SUCCESS_STATUS_MSG("创建订单成功"),

    CHARGE_CALLBACK_SUCCESS("True"),

    FORMAT("json"),

    VERSION14("1.4"),

    VERSION10("1.0");


    private FlConstantsEnums(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return this.value;
    }

}
