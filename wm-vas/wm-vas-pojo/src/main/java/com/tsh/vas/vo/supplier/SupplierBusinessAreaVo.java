package com.tsh.vas.vo.supplier;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/10/10.
 */
public class SupplierBusinessAreaVo {

    /**
     * 业务编码
     */
    private String businessCode;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 服务区域列表
     */
    private List<String> supplierAreas;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public List<String> getSupplierAreas() {
        return supplierAreas;
    }

    public void setSupplierAreas(List<String> supplierAreas) {
        this.supplierAreas = supplierAreas;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
