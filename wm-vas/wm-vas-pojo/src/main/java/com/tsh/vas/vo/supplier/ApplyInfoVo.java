package com.tsh.vas.vo.supplier;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public class ApplyInfoVo {

    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 商家系统中的供应商id
     */
    private Long shopSupplierId;
    /**
     * 商家系统中的供应商编号
     */
    private String shopSupplierNo;
    /**
     * 申请说明
     */
    private String applyExplain;
    /**
     * 业务类型
     */
    private Integer businessType;
    /**
     * 供应商公司
     */
    private String company;
    /**
     * 供应商邮箱
     */
    private String email;
    /**
     * 供应商手机号码
     */
    private String mobile;
    /**
     * 供应商姓名
     */
    private String supplierName;
    /**
     * 供应商座机
     */
    private String telphone;
    /**
     * 供应商服务业务列表字符串
     */
    private List<String> businessCodes;
    
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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

    public String getApplyExplain() {
        return applyExplain;
    }

    public void setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public List<String> getBusinessCodes() {
        return businessCodes;
    }

    public void setBusinessCodes(List<String> businessCodes) {
        this.businessCodes = businessCodes;
    }
    
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
