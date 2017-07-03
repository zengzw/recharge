package com.tsh.vas.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "charge_payment_info")
public class ChargePaymentInfo implements Serializable {

    /**
     * 缴费单支付信息表
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 缴费订单支付流水号
     */
    @Basic
    @Column(name = "charge_code")
    private String chargeCode = "";
    /**
     * 实付金额
     */
    @Basic
    @Column(name = "real_amount")
    private BigDecimal realAmount = new BigDecimal (0);
    /**
     * 支付方式：1：会员支付，2：网点支付
     */
    @Basic
    @Column(name = "pay_way")
    private Integer payWay = 0;
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
