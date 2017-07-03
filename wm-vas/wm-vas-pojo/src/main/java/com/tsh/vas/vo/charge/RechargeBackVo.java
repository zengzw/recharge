package com.tsh.vas.vo.charge;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/10/15.
 */
public class RechargeBackVo implements Serializable {

    /**
     * 执行结果，1.充值成功，2，充值失败 ,3.进行中，4.异常订单
     */
    private Integer responseCode;
    /**
     * 缴费订单编号，增值服务的订单编号，不是第三方平台的订单编号
     */
    private String chargeCode;
    /**
     * 实际充值金额
     */
    private String message;
    /**
     * 第三方平台的订单编号
     */
    private String openPlatformNo;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpenPlatformNo() {
        return openPlatformNo;
    }

    public void setOpenPlatformNo(String openPlatformNo) {
        this.openPlatformNo = openPlatformNo;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
