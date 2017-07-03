package com.tsh.vas.model.trainticket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Entity
@Table(name = "hcp_payment_record")
public class HcpPaymentRecordPo implements Serializable{

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    @Column(name = "pay_type")
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType =payType;
    }
    @Column(name = "hcp_order_code")
    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode =hcpOrderCode;
    }
    @Column(name = "pay_amount")
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount =payAmount;
    }
    @Column(name = "pay_record")
    public String getPayRecord() {
        return payRecord;
    }

    public void setPayRecord(String payRecord) {
        this.payRecord =payRecord;
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
