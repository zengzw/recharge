package com.tsh.vas.vo.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class HcpRefundAmountVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -670789940272647777L;
    /**  退票支付id*/
    private Integer id;
    /**  购票订单编号*/
    private String hcpOrderCode;

    /**  退票订单编号*/
    private String refundAmountCode;

    /**
     * 退票订单号
     */
    private String RefundTicketCode;
    
    
    /**  退款类型(1：购票退款，2：退票退款)*/
    private Integer refundType;

    /**  退款金额*/
    private BigDecimal realAmount;

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




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode =hcpOrderCode;
    }
   
    public String getRefundAmountCode() {
        return refundAmountCode;
    }

    public void setRefundAmountCode(String refundAmountCode) {
        this.refundAmountCode = refundAmountCode;
    }

    public String getRefundTicketCode() {
        return RefundTicketCode;
    }

    public void setRefundTicketCode(String refundTicketCode) {
        RefundTicketCode = refundTicketCode;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType =refundType;
    }
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount =realAmount;
    }
    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay =payWay;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode =userCode;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName =userName;
    }
    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile =userMobile;
    }
    public Integer getRefundTimes() {
        return refundTimes;
    }

    public void setRefundTimes(Integer refundTimes) {
        this.refundTimes =refundTimes;
    }
    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime =refundTime;
    }
    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc =refundDesc;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
}
