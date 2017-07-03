package com.tsh.vas.vo.charge;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/10/9.
 */
public class ChargePayBackVo {

    /**
     * 支付状态，1.成功， 2.失败
     */
    private Integer status;
    /**
     * 支付流水号
     */
    private String serialNumber;
    /**
     * 缴费单编号
     */
    private String chargeCode;
    /**
     * 实付金额
     */
    private BigDecimal realAmount;
    /**
     * 备注信息
     */
    private String remark;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
