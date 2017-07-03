package com.tsh.vas.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "vas_order_transform_history")
public class VasOrderTransformHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8006226195076729744L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 订单类型
     */
    @Basic
    @Column(name = "order_type")
    private String orderType;

    /**
     * 订单编号
     */
    @Basic
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 创建人ID
     */
    @Basic
    @Column(name = "create_id")
    private Integer createId;

    /**
     * 创建人
     */
    @Basic
    @Column(name = "creater")
    private String creater;

    @Basic
    @Column(name = "update_time")
    private String updateTime;

    @Basic
    @Column(name = "before_status")
    private String beforeStatus;

    @Basic
    @Column(name = "after_status")
    private String afterStatus;

    @Basic
    @Column(name = "remark")
    private String remark;
    
    @Basic
    @Column(name="pay_money")
    private BigDecimal payMoney;

    public String getAfterStatus() {
        return afterStatus;
    }

    public String getBeforeStatus() {
        return beforeStatus;
    }


    public Integer getCreateId() {
        return createId;
    }

    public String getCreater() {
        return creater;
    }

    public Long getId() {
        return id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getOrderType() {
        return orderType;
    }
    
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public String getRemark() {
        return remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setAfterStatus(String afterStatus) {
        this.afterStatus = afterStatus;
    }

    public void setBeforeStatus(String beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
