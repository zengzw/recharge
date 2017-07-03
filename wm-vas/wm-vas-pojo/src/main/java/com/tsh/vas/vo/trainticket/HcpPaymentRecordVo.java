package com.tsh.vas.vo.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class HcpPaymentRecordVo implements Serializable{
    private static final long serialVersionUID = 1L;

    /**  订单支付ID*/
    private Integer id;
    /**  支付类型(1：订单支付，2：退票支付)*/
    private Integer payType;

    /**  火车票订单编号*/
    private String hcpOrderCode;

    /**  实付金额*/
    private BigDecimal payAmount;

    /**  支付流水*/
    private String payRecord;

    /**  支付方法（1：会员支付，2：网点支付）*/
    private Integer payWay;

    /**  创建时间*/
    private Date createTime;

    /**  备注*/
    private String remark;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType =payType;
    }
    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode =hcpOrderCode;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount =payAmount;
    }
    public String getPayRecord() {
        return payRecord;
    }

    public void setPayRecord(String payRecord) {
        this.payRecord =payRecord;
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
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
}
