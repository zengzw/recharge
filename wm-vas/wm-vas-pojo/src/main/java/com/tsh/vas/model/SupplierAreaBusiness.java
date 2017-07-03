package com.tsh.vas.model;

import com.dtds.platform.commons.utility.DateUtil;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "supplier_area_business")
public class SupplierAreaBusiness implements Serializable {

    /**
     * 供应商可服务区域id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 省
     */
    @Basic
    @Column(name = "province")
    private String province = "";
    /**
     * 市
     */
    @Basic
    @Column(name = "city")
    private String city = "";
    /**
     * 县
     */
    @Basic
    @Column(name = "country")
    private String country = "";
    /**
     * 供应商编号
     */
    @Basic
    @Column(name = "supplier_code")
    private String supplierCode = "";
    /**
     * 服务业务编码
     */
    @Basic
    @Column(name = "business_code")
    private String businessCode = "";
    /**
     * 供应商排序比重
     */
    @Basic
    @Column(name = "supplier_order")
    private Integer supplierOrder = 0;
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 县域运营中心编号id
     */
    @Basic
    @Column(name = "country_code")
    private String countryCode = "";
    /**
     * 县域运营中心名称
     */
    @Basic
    @Column(name = "country_name")
    private String countryName = "";

    /**
     * 转换相应的日期格式
     *
     * @return
     */
    public String getCreateTimeStr() {
        return DateUtil.date2String (createTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public Integer getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(Integer supplierOrder) {
        this.supplierOrder = supplierOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
