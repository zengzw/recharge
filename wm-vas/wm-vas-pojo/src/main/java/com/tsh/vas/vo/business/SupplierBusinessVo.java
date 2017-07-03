package com.tsh.vas.vo.business;

import com.tsh.vas.model.SupplierBusiness;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/27.
 */
public class SupplierBusinessVo extends SupplierBusiness implements Serializable {

    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 业务名称
     */
    private String businessName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
