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
 * 退票PO
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Entity
@Table(name = "hcp_refund_ticket")
public class HcpRefundTicketPo implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = -3126281792441341320L;
    /**  退票单ID*/
    private Integer id;
    /**  购票订单编号*/
    private String hcpOrderCode;
    /**  退票编号*/
    private String refundCode;
    /**  实际退票金额*/
    private BigDecimal realAmount;
    /**  应退款金额*/
    private BigDecimal refundAmount;
    /**  供应商退票流水*/
    private String supplierRecord;
    /**  合作商户退票流水*/
    private String myRecord;
    /**  创建时间*/
    private Date createTime;
    /**  备注*/
    private String remark;
    /**  退票状态(1：待退票，2：退票中，5：退票失败，13：退票成功/待退款，14：关闭)*/
    private Integer refundStatus;
    
    
    private String failReason;

  

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }
    
    @Column(name="fail_reason")
    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    @Column(name = "hcp_order_code")
    public String getHcpOrderCode() {
        return hcpOrderCode;
    }

    public void setHcpOrderCode(String hcpOrderCode) {
        this.hcpOrderCode =hcpOrderCode;
    }
    @Column(name = "refund_code")
    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode =refundCode;
    }
    @Column(name = "real_amount")
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount =realAmount;
    }
    @Column(name = "refund_amount")
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount =refundAmount;
    }
    @Column(name = "supplier_record")
    public String getSupplierRecord() {
        return supplierRecord;
    }

    public void setSupplierRecord(String supplierRecord) {
        this.supplierRecord =supplierRecord;
    }
   
    @Transient
    public String getMyRecord() {
        return myRecord;
    }

    public void setMyRecord(String myRecord) {
        this.myRecord =myRecord;
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
    @Column(name = "refund_status")
    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus =refundStatus;
    }
}
