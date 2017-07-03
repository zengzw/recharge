package com.tsh.vas.vo.charge;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/10/19.
 */
public class BillInfoVo {

    /**
     * 缴费机构编码
     */
    private String chargeOrgCode;
    /**
     * 缴费机构名称
     */
    private String chargeOrgName;
    /**
     * 账单号
     */
    private String account;
    /**
     * 缴费产品ID
     */
    private String productId;
    /**
     * 缴费类型	1.水费2.电费3.煤气
     */
    private String payType;
    /**
     * 支持账户类型  1.户号 2.条形码
     */
    private String accountType;
    /**
     * 省名
     */
    private String province;
    /**
     * 市名
     */
    private String city;
    /**
     * 县名
     */
    private String county;


    public String getChargeOrgCode() {
        return chargeOrgCode;
    }

    public void setChargeOrgCode(String chargeOrgCode) {
        this.chargeOrgCode = chargeOrgCode;
    }

    public String getChargeOrgName() {
        return chargeOrgName;
    }

    public void setChargeOrgName(String chargeOrgName) {
        this.chargeOrgName = chargeOrgName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
