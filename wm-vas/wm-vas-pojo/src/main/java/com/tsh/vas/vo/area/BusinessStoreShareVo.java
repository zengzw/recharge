package com.tsh.vas.vo.area;


import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 佣金占比
 *
 * @version 1.0.0 2016年10月13日<br>
 * @see
 * @since JDK 1.7.0
 */
public class BusinessStoreShareVo implements Serializable {

    /**
     * 服务类型名称
     */
    private String businessName;
    /**
     * 服务类型编码
     */
    private String businessCode;
    /**
     * 县域运营中心编号
     */
    private String countryCode;
    /**
     * 县域运营中心名称
     */
    private String countryName;
    /**
     * 总利润分成百分比率
     */
    private Double countryShareRatio;
    /**
     * 总利润分成百分比率
     */
    private Double storeShareRatio;

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
