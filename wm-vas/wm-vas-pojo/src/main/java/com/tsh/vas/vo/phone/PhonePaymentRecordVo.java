package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class PhonePaymentRecordVo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = 791005693446957704L;
    
    /**  订单支付ID*/
    private Long id;
    
    /**  支付类型(1：订单支付，2：退票支付)*/
    private Integer payType;

    /**  火车票订单编号*/
    private String phoneOrderCode;

    /**  实付金额*/
    private BigDecimal payAmount;

    /**   2:平台管理 3:县域 4:网点，5：供应商 9：会员*/
    private Integer payWay;

    /**  创建时间*/
    private Date createTime;

    /**  备注*/
    private String remark;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType =payType;
    }
    public String getPhoneOrderCode() {
        return phoneOrderCode;
    }

    public void setPhoneOrderCode(String phoneOrderCode) {
        this.phoneOrderCode =phoneOrderCode;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount =payAmount;
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
