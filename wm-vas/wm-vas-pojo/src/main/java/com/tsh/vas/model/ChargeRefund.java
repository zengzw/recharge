package com.tsh.vas.model;

import com.dtds.platform.commons.utility.DateUtil;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/10/15.
 */
@Entity
@Table(name = "charge_refund")
public class ChargeRefund implements Serializable {

    /**
     * 缴费单退款id,
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 退款单编号
     */
    @Basic
    @Column(name = "refund_code")
    private String refundCode = "";
    /**
     * 缴费订单编号
     */
    @Basic
    @Column(name = "charge_code")
    private String chargeCode = "";
    /**
     * '退款金额'
     */
    @Basic
    @Column(name = "refund_amount")
    private BigDecimal refundAmount = new BigDecimal (0);

    /**
     * 退款时间
     */
    @Basic
    @Column(name = "refund_time")
    private Date refundTime;
    /**
     * 退款操作用户姓名
     */
    @Basic
    @Column(name = "user_name")
    private String userName = "";
    /**
     * 退款操作用户手机号码
     */
    @Basic
    @Column(name = "user_mobile")
    private String userMobile = "";

    /**
     * 退款操作用户编号
     */
    @Basic
    @Column(name = "user_code")
    private String userCode = "";
    /**
     * 退款备注信息
     */
    @Basic
    @Column(name = "remark")
    private String remark = "";

    /**
     * 返回相应的时间格式
     *
     * @return
     */
    public String getRefundTimeStr() {
        return DateUtil.date2String (refundTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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
