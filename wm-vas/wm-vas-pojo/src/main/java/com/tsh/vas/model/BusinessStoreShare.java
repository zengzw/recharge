package com.tsh.vas.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Iritchie.ren on 2016/9/21.
 */
@Entity
@Table(name = "business_store_share")
public class BusinessStoreShare implements Serializable {

    /**
     * 服务业务网点信息表
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 服务类型编码
     */
    @Basic
    @Column(name = "business_code")
    private String businessCode = "";
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
     * 总利润分成百分比率
     */
    @Basic
    @Column(name = "country_share_ratio")
    private Double countryShareRatio = 0.00;
    /**
     * 总利润分成百分比率
     */
    @Basic
    @Column(name = "store_share_ratio")
    private Double storeShareRatio = 0.00;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getCountryShareRatio() {
        return countryShareRatio;
    }

    public void setCountryShareRatio(Double countryShareRatio) {
        this.countryShareRatio = countryShareRatio;
    }

    public Double getStoreShareRatio() {
        return storeShareRatio;
    }

    public void setStoreShareRatio(Double storeShareRatio) {
        this.storeShareRatio = storeShareRatio;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
