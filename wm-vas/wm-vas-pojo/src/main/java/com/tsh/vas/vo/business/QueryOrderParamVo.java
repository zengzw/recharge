package com.tsh.vas.vo.business;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 订单报表Vo
 *
 * @author yangpeng <br>
 * @version 1.0.0 2016年10月14日<br>
 * @see
 * @since JDK 1.7.0
 */
public class QueryOrderParamVo {

    private Integer rows;

    private Integer page;
    /**
     * 网点名称
     */
    private String storeName;
    /**
     * 县运营中心编号id
     */
    private String countryCode;
    /**
     * 子分类编码
     */
    private String subBusinessCode;
    /**
     * 服务业务编号,增值服务类型
     */
    private String businessCode;
    /**
     * 缴费订单编号
     */
    private String chargeCode;
    /**
     * 下单开始时间
     */
    private String startDate;
    /**
     * 下单结束时间
     */
    private String endDate;
    /**
     * 支付状态：1：未支付。2：已支付。3：支付中。4：支付失败
     */
    private Integer payStatus;
    /**
     * 退款状态：1.待退款 。2.已退款
     */
    private Integer refundStatus;
    /**
     * 付费用户编号,充值缴费账号
     */
    private String rechargeUserCode;
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
     * 供应商名称,服务供应商
     */
    private String supplierName;
    /**
     * 缴费用户姓名
     */
    private String rechargeUserName;
    /**
     * 充值缴费联系人电话
     */
    private String mobile;
    /**
     * 县运营中心名称
     */
    private String countryName;
    /**
     * 会员手机号码
     */
    private String memberMobile;
    /**
     * 会员姓名
     */
    private String memberName;
    
    public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSubBusinessCode() {
        return subBusinessCode;
    }

    public void setSubBusinessCode(String subBusinessCode) {
        this.subBusinessCode = subBusinessCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRechargeUserCode() {
        return rechargeUserCode;
    }

    public void setRechargeUserCode(String rechargeUserCode) {
        this.rechargeUserCode = rechargeUserCode;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getRechargeUserName() {
        return rechargeUserName;
    }

    public void setRechargeUserName(String rechargeUserName) {
        this.rechargeUserName = rechargeUserName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}

