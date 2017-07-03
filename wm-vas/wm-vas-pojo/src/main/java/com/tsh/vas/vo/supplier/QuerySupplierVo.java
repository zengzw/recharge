package com.tsh.vas.vo.supplier;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public class QuerySupplierVo {

    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商姓名
     */
    private String supplierName;
    /**
     * 供应商公司
     */
    private String company;
    /**
     * 页码
     */
    private Integer page;
    /**
     * 页大小
     */
    private Integer rows;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
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
