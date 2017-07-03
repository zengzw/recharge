package com.tsh.vas.vo.area;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/9/28.
 */
public class AreaBusinessProfitVo implements Serializable {

    /**
     * 县域运营中心编号
     */
    private String countryCode;
    /**
     * 县域运营中心名称
     */
    private String countryName;
    /**
     * 服务业务编码
     */
    private String businessCode;
    /**
     * 服务业务名称
     */
    private String businessName;
    /**
     * 县域分层比例
     */
    private Double countryShareRatio;
    /**
     * 网点分成比例
     */
    private Double storeShareRatio;

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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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
