package com.tsh.vas.model.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 退款 PO
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Entity
@Table(name = "hcp_refund_amount")
public class HcpRefundAmountPo implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = -7260155177661774008L;
    /**  退票支付id*/
    private Integer id;
    /**  购票订单编号*/
    private String hcpOrderCode;
    /**  退票订单编号*/
    private String refundAmountCode;
    
    /**  退款类型(1：购票退款，2：退票退款)*/
    private Integer refundType;
    /**  退款金额*/
    private BigDecimal realAmount;
    
    /**
     * 退票订单号
     */
    private String RefundTicketCode;
    
    /**
     * 退款状态
     */
    private Integer status;
    
    /**  支付方式*/
    private Integer payWay;

    /**  创建时间*/
    private Date createTime;

    /**  操作用户编号*/
    private String userCode;
    /**  操作用户姓名*/
    private String userName;
    /**  操作用户手机号*/
    private String userMobile;
    /**  退款次数*/
    private Integer refundTimes;
    /**  退款时间*/
    private Date refundTime;
    
    
    /**  退款描述*/
    private String refundDesc;
    /**  备注*/
    private String remark;
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }
    @Column(name = "hcp_order_code")
    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }
    @Transient
    public Integer getPayWay() {
        return payWay;
    }
    @Column(name = "real_amount")
    public BigDecimal getRealAmount() {
        return realAmount;
    }
    
    @Column(name="refund_amount_code")
    public String getRefundAmountCode() {
        return refundAmountCode;
    }
    @Column(name = "refund_desc")
    public String getRefundDesc() {
        return refundDesc;
    }

    @Column(name="hcp_refund_ticket_code")
    public String getRefundTicketCode() {
        return RefundTicketCode;
    }

    @Column(name = "refund_time")
    public Date getRefundTime() {
        return refundTime;
    }
    @Column(name = "refund_times")
    public Integer getRefundTimes() {
        return refundTimes;
    }

    @Column(name = "refund_type")
    public Integer getRefundType() {
        return refundType;
    }
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    @Column(name="status")
    public Integer getStatus() {
        return status;
    }
    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    @Column(name = "user_mobile")
    public String getUserMobile() {
        return userMobile;
    }
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode =hcpOrderCode;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    public void setPayWay(Integer payWay) {
        this.payWay =payWay;
    }

  
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount =realAmount;
    }

    public void setRefundAmountCode(String refundAmountCode) {
        this.refundAmountCode = refundAmountCode;
    }
    public void setRefundDesc(String refundDesc) {
        this.refundDesc =refundDesc;
    }

    public void setRefundTicketCode(String refundTicketCode) {
        RefundTicketCode = refundTicketCode;
    }
    public void setRefundTime(Date refundTime) {
        this.refundTime =refundTime;
    }

    public void setRefundTimes(Integer refundTimes) {
        this.refundTimes =refundTimes;
    }
    public void setRefundType(Integer refundType) {
        this.refundType =refundType;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUserCode(String userCode) {
        this.userCode =userCode;
    }
    public void setUserMobile(String userMobile) {
        this.userMobile =userMobile;
    }

    public void setUserName(String userName) {
        this.userName =userName;
    }
}
