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
@Table(name = "phone_payment_record")
public class PhonePaymentRecordPo implements Serializable{


    /**
     * 
     */
    private static final Long serialVersionUID = -4277010928414288490L;
    
    
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    @Column(name = "pay_type")
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType =payType;
    }
    @Column(name = "phone_order_code")
    public String getPhoneOrderCode() {
        return phoneOrderCode;
    }

    public void setPhoneOrderCode(String phoneOrderCode) {
        this.phoneOrderCode =phoneOrderCode;
    }
    @Column(name = "pay_amount")
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount =payAmount;
    }
    @Column(name = "pay_way")
    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay =payWay;
    }
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
}
