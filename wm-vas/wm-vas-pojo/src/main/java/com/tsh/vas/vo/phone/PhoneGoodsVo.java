package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class PhoneGoodsVo implements Serializable{
    /**
     * 
     */
    private static final Long serialVersionUID = 8222960508430124294L;
    
    
    /**  商品Id*/
    private Long id;
    /**  运营商编号*/
    private String supplierCode;

    /**  充值面额*/
    private Integer phoneValue;

    /**  实付金额*/
    private BigDecimal saleAmount;

    /**  发布状态（0：未发布，1：已发布，2：取消发布）*/
    private Integer status;

    /**  创建人*/
    private String creater;

    /**  创建人Id*/
    private Integer createId;

    /**  创建时间*/
    private Date createTime;

    /**  修改人*/
    private String modifyer;

    /**  修改人Id*/
    private Integer modifyId;

    /**  最后修改时间*/
    private Date modifyTime;

    /**  备注*/
    private String remark;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode =supplierCode;
    }
    public Integer getPhoneValue() {
        return phoneValue;
    }

    public void setPhoneValue(Integer phoneValue) {
        this.phoneValue =phoneValue;
    }
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount =saleAmount;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status =status;
    }
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater =creater;
    }
    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId =createId;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    public String getModifyer() {
        return modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer =modifyer;
    }
    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId =modifyId;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime =modifyTime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
}
