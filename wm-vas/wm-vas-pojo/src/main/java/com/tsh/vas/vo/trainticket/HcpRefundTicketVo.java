package com.tsh.vas.vo.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class HcpRefundTicketVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6627896615927242668L;
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
    

    /**  退票状态(1：待退票，2：供应商退票成功，待退款，3：退款成功，4：供应商退票失败，5：扣款失败)*/
    private Integer refundStatus;




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
    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode =refundCode;
    }
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount =realAmount;
    }
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount =refundAmount;
    }
    public String getSupplierRecord() {
        return supplierRecord;
    }

    public void setSupplierRecord(String supplierRecord) {
        this.supplierRecord =supplierRecord;
    }
    public String getMyRecord() {
        return myRecord;
    }

    public void setMyRecord(String myRecord) {
        this.myRecord =myRecord;
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
    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus =refundStatus;
    }
}
