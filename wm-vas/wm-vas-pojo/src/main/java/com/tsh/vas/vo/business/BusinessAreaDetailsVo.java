package com.tsh.vas.vo.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/26.
 */
public class BusinessAreaDetailsVo implements Serializable {

    /**
     * 服务业务姓名
     */
    private String businessName;
    /**
     * 服务业务编号
     */
    private String businessCode;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 县
     */
    private String country;
    /**
     * 县域运营中心名称
     */
    private String countryName;
    /**
     * 县域运营中心编码
     */
    private String countryCode;
    /**
     * 供应商列表字符串
     */
    private String supplierStr;
    /**
     * 供应商列表
     */
    private List<Supplier> suppliers;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSupplierStr() {
        return supplierStr;
    }

    public void setSupplierStr(String supplierStr) {
        this.supplierStr = supplierStr;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public static class Supplier {

        private String supplierCode;
        private String company;
        private Integer supplierOrder;
        private Long id;

        public String getSupplierCode() {
            return supplierCode;
        }

        public void setSupplierCode(String supplierCode) {
            this.supplierCode = supplierCode;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public Integer getSupplierOrder() {
            return supplierOrder;
        }

        public void setSupplierOrder(Integer supplierOrder) {
            this.supplierOrder = supplierOrder;
        }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
        
    }
}
