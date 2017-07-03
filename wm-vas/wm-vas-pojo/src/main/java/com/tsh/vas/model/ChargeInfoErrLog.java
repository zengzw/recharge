package com.tsh.vas.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "charge_info_err_log")
public class ChargeInfoErrLog implements Serializable {

    /**
     * 缴费单信息id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 缴费订单编号
     */
    @Basic
    @Column(name = "charge_code")
    private String chargeCode = "";
    /**
     * 开放平台的订单编号
     */
    @Basic
    @Column(name = "open_platform_no")
    private String openPlatformNo = "";
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "refund_status")
    private Integer refundStatus = 0;
    /**
     * 退款状态：0.初始状态，未发生退款，1.退款中 .已退款，3，退款失
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 支付状态：1：待支付：支付中：缴费中：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销
     */
    @Basic
    @Column(name = "pay_status")
    private Integer payStatus = 0;
    /**
     * 记录标记说明
     */
    @Basic
    @Column(name = "remark")
    private String remark = "";

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

    public String getOpenPlatformNo() {
        return openPlatformNo;
    }

    public void setOpenPlatformNo(String openPlatformNo) {
        this.openPlatformNo = openPlatformNo;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
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
