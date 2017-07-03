package com.tsh.vas.vo.business;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/27.
 */
public class BusinessSupplierQueryVo implements Serializable {

    /**
     * 服务业务编码
     */
    private String businessCode;
    /**
     * 供应商姓名
     */
    private String supplierName;
    /**
     * 供应商公司名
     */
    private String company;
    

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
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
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
