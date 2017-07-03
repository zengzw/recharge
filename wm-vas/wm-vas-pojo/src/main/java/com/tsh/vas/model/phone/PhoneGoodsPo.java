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
@Table(name = "phone_goods")
public class PhoneGoodsPo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -8833844689531590056L;


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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    @Column(name = "supplier_code")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode =supplierCode;
    }
    @Column(name = "phone_value")
    public Integer getPhoneValue() {
        return phoneValue;
    }

    public void setPhoneValue(Integer phoneValue) {
        this.phoneValue =phoneValue;
    }
    @Column(name = "sale_amount")
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount =saleAmount;
    }
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status =status;
    }
    @Column(name = "creater")
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater =creater;
    }
    @Column(name = "create_id")
    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId =createId;
    }
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    @Column(name = "modifyer")
    public String getModifyer() {
        return modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer =modifyer;
    }
    @Column(name = "modify_id")
    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId =modifyId;
    }
    @Column(name = "modify_time")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime =modifyTime;
    }
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark =remark;
    }
}
