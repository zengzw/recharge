package com.tsh.vas.model;

import com.dtds.platform.commons.utility.DateUtil;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "supplier_info")
public class SupplierInfo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4433039192289275301L;
    
    /**
     * 供应商信息表
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 商家系统中的供应商id
     */
    @Basic
    @Column(name = "shop_supplier_id")
    private Long shopSupplierId = 0L;
    /**
     * 商家系统中的供应商编号
     */
    @Basic
    @Column(name = "shop_supplier_no")
    private String shopSupplierNo = "";
    /**
     * 供应商编码
     */
    @Basic
    @Column(name = "supplier_code")
    private String supplierCode = "";
    /**
     * 供应商名称
     */
    @Basic
    @Column(name = "supplier_name")
    private String supplierName = "";
    /**
     * 公司名称
     */
    @Basic
    @Column(name = "company")
    private String company = "";
    /**
     * 供应商邮箱
     */
    @Basic
    @Column(name = "email")
    private String email = "";
    /**
     * 供应商手机号码
     */
    @Basic
    @Column(name = "mobile")
    private String mobile = "";
    /**
     * 供应商座机联系
     */
    @Basic
    @Column(name = "telphone")
    private String telphone = "";
    /**
     * 供应商申请对接说明
     */
    @Basic
    @Column(name = "Apply_explain")
    private String applyExplain = "";
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 审核状态;1.待审核。2.已审核。3.已终止服务 。4.已经删除
     */
    @Basic
    @Column(name = "check_status")
    private Integer checkStatus = 0;
    /**
     * 供应商审核时间
     */
    @Basic
    @Column(name = "check_time")
    private Date checkTime;

    /**
     * 转换相应的日期格式
     *
     * @return
     */
    public String getCreateTimeStr() {
        return DateUtil.date2String (createTime);
    }

    /**
     * 转换相应的日期格式
     *
     * @return
     */
    public String getCheckTimeStr() {
        return DateUtil.date2String (checkTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopSupplierId() {
        return shopSupplierId;
    }

    public void setShopSupplierId(Long shopSupplierId) {
        this.shopSupplierId = shopSupplierId;
    }

    public String getShopSupplierNo() {
        return shopSupplierNo;
    }

    public void setShopSupplierNo(String shopSupplierNo) {
        this.shopSupplierNo = shopSupplierNo;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getApplyExplain() {
        return applyExplain;
    }

    public void setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
