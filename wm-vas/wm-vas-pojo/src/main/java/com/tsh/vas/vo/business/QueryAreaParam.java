package com.tsh.vas.vo.business;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/23.
 */
public class QueryAreaParam implements Serializable {

    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 服务业务编码
     */
    private String businessCode;
    /**
     *
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 县域运营中心编码
     */
    private String countryCode;
    /**
     * 县域运营中心名称
     */
    private String countryName;
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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
