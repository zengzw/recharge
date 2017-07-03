package com.tsh.phone.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "phone_rt_business")
public class PhoneRTBusinessPo implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 8601669420729728111L;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id =id;
    }
    @Column(name = "business_code")
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode =businessCode;
    }
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province =province;
    }
    @Column(name = "supplier_type")
    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType =supplierType;
    }
    
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort =sort;
    }
}
