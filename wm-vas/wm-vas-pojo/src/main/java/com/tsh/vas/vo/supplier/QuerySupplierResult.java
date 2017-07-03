package com.tsh.vas.vo.supplier;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.tsh.vas.model.SupplierInfo;

/**
 * Created by Iritchie.ren on 2016/9/22.
 */
public class QuerySupplierResult extends SupplierInfo {

    /**
     * 业务列表名称
     */
    private String businessNames;
    /**
     * 业务服务区域说明
     */
    private String supplierAreaDesc;

    public String getBusinessNames() {
        return businessNames;
    }

    public void setBusinessNames(String businessNames) {
        this.businessNames = businessNames;
    }

    public String getSupplierAreaDesc() {
        return supplierAreaDesc;
    }

    public void setSupplierAreaDesc(String supplierAreaDesc) {
        this.supplierAreaDesc = supplierAreaDesc;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
