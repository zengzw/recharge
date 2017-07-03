package com.tsh.vas.model.phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "phone_refund_amount")
public class PhoneRefundAmountPo implements Serializable{
	
	
		/**
     * 
     */
    private static final Long serialVersionUID = -4228626846678285096L;
        /**  退款支付id*/
	private Long id;
		/**  退款编号*/
	private String refundAmountCode;
		/**  购票订单编号*/
	private String phoneOrderCode;
		/**  退款类型(1：充值失败 2：支付异常退款)*/
	private Integer refundType;
		/**  退款金额*/
	private BigDecimal realAmount;
		/**  退款状态（3：待退款，12：退款中，13：退款成功，15：退款失败）*/
	private Integer status;
		/**  创建时间*/
	private Date createTime;
		/**  退款次数，最大20*/
	private Integer refundTimes;
		/**  退款时间*/
	private Date refundTime;
		/**  备注*/
	private String remark;
		
			@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id =id;
	}
				@Column(name = "refund_amount_code")
	public String getRefundAmountCode() {
		return refundAmountCode;
	}

	public void setRefundAmountCode(String refundAmountCode) {
		this.refundAmountCode =refundAmountCode;
	}
				@Column(name = "phone_order_code")
	public String getPhoneOrderCode() {
		return phoneOrderCode;
	}

	public void setPhoneOrderCode(String phoneOrderCode) {
		this.phoneOrderCode =phoneOrderCode;
	}
				@Column(name = "refund_type")
	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType =refundType;
	}
				@Column(name = "real_amount")
	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount =realAmount;
	}
				@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status =status;
	}
				@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =createTime;
	}
				@Column(name = "refund_times")
	public Integer getRefundTimes() {
		return refundTimes;
	}

	public void setRefundTimes(Integer refundTimes) {
		this.refundTimes =refundTimes;
	}
				@Column(name = "refund_time")
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime =refundTime;
	}
				@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark =remark;
	}
		}
