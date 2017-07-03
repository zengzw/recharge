package com.tsh.phone.vo;

import java.io.Serializable;
import java.util.Date;


public class PhoneRTBusinessVo implements Serializable{

    /**  */
    private Integer id;
    /**  业务编码*/
    private String businessCode;

    /**  省份*/
    private String province;

    /**  服务商类型*/
    private String supplierType;

    /**  */
    private Integer sort;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id =id;
    }
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode =businessCode;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province =province;
    }
    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType =supplierType;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort =sort;
    }
}
