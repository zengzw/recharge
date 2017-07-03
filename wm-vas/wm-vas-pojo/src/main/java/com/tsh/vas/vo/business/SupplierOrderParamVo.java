package com.tsh.vas.vo.business;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/26.
 */
public class SupplierOrderParamVo implements Serializable {

    /**
     * 业务编码
     */
    private String businessCode;
    /**
     * 县域运营中心编码
     */
    private String countryCode;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商排序权重
     */
    private Integer supplierOrder;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(Integer supplierOrder) {
        this.supplierOrder = supplierOrder;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
