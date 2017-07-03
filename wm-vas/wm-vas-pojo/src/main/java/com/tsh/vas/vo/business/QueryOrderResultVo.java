package com.tsh.vas.vo.business;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 订单报表Vo
 *
 * @author yangpeng <br>
 * @version 1.0.0 2016年10月14日<br>
 * @see
 * @since JDK 1.7.0
 */
public class QueryOrderResultVo {

    /**
     * 订单id
     */
    private Long id;
    /**
     * 缴费订单编号
     */
    private String chargeCode;
    /**
     * 供应商编号
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 服务业务编号
     */
    private String businessCode;
    /**
     * 服务业务名称
     */
    private String businessName;
    /**
     * 收费机构名称
     */
    private String chargeOrgName;
    /**
     * 付费用户编号
     */
    private String rechargeUserCode;
    /**
     * 付费用户名称
     */
    private String rechargeUserName;
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
     * 县运营中心编号id
     */
    private String countryCode;
    /**
     * 县运营中心名称
     */
    private String countryName;
    /**
     * 网点名称
     */
    private String storeName;
    /**
     * 成本价
     */
    private BigDecimal costingAmount = new BigDecimal (0);
    /**
     * 应缴金额
     */
    private BigDecimal originalAmount = new BigDecimal (0);
    /**
     * 实缴金额
     */
    private BigDecimal realAmount = new BigDecimal (0);
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *
     */
    private String createTimeStr;
    /**
     * 退款状态：1.待退款 。2.已退款
     */
    private Integer refundStatus;
    /**
     * 支付状态：1：未支付。2：已支付。3：支付中。4：支付失败
     */
    private Integer payStatus;
    /**
     * 支付方式：1：会员支付，2：网点支付
     */
    private Integer payWay;
    /**
     * 子分类编码
     */
    private String subBusinessCode;
    /**
     * 子分类名称
     */
    private String subBusinessName;
    /**
     * 下单开始时间
     */
    private String startDate;
    /**
     * 下单结束时间
     */
    private String endDate;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount = new BigDecimal (0);
    /**
     * 退款单号
     */
    private String refundCode;
    /**
     * 提点系数
     */
    private String liftCoefficient;
    /**
     * 平台和县域分成：县域平台分成比
     */
    private Double platformDividedRatio;
    /**
     * 平台和县域分成：县域分成比
     */
    private Double areaDividedRatio;
    /**
     * 县域和网点分成：县域佣金比
     */
    private Double areaCommissionRatio;
    /**
     * 县域和网点分成：网点佣金比
     */
    private Double storeCommissionRatio;
    /**
     * 充值缴费输入的会员联系人电话
     */
    private String mobile;
    /**
     * 充值缴费输入的会员联系人姓名
     */
    private String payUserName;
    /**
     * 平台分成（元）
     */
    private String platformDividedYuan;
    /**
     * 县域佣金（元）
     */
    private String areaCommissionYuan;
    /**
     * 网点佣金（元）
     */
    private String storeCommissionYuan;
    /**
     * 会员手机号码
     */
    private String memberMobile;
    /**
     * 会员姓名
     */
    private String memberName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getChargeOrgName() {
        return chargeOrgName;
    }

    public void setChargeOrgName(String chargeOrgName) {
        this.chargeOrgName = chargeOrgName;
    }

    public String getRechargeUserCode() {
        return rechargeUserCode;
    }

    public void setRechargeUserCode(String rechargeUserCode) {
        this.rechargeUserCode = rechargeUserCode;
    }

    public String getRechargeUserName() {
        return rechargeUserName;
    }

    public void setRechargeUserName(String rechargeUserName) {
        this.rechargeUserName = rechargeUserName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public BigDecimal getCostingAmount() {
        return costingAmount;
    }

    public void setCostingAmount(BigDecimal costingAmount) {
        this.costingAmount = costingAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public String getSubBusinessCode() {
        return subBusinessCode;
    }

    public void setSubBusinessCode(String subBusinessCode) {
        this.subBusinessCode = subBusinessCode;
    }

    public String getSubBusinessName() {
        return subBusinessName;
    }

    public void setSubBusinessName(String subBusinessName) {
        this.subBusinessName = subBusinessName;
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

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public Double getPlatformDividedRatio() {
        return platformDividedRatio;
    }

    public void setPlatformDividedRatio(Double platformDividedRatio) {
        this.platformDividedRatio = platformDividedRatio;
    }

    public Double getAreaDividedRatio() {
        return areaDividedRatio;
    }

    public void setAreaDividedRatio(Double areaDividedRatio) {
        this.areaDividedRatio = areaDividedRatio;
    }

    public Double getAreaCommissionRatio() {
        return areaCommissionRatio;
    }

    public void setAreaCommissionRatio(Double areaCommissionRatio) {
        this.areaCommissionRatio = areaCommissionRatio;
    }

    public Double getStoreCommissionRatio() {
        return storeCommissionRatio;
    }

    public void setStoreCommissionRatio(Double storeCommissionRatio) {
        this.storeCommissionRatio = storeCommissionRatio;
    }

    public String getLiftCoefficient() {
        return liftCoefficient;
    }

    public void setLiftCoefficient(String liftCoefficient) {
        this.liftCoefficient = liftCoefficient;
    }

    public String getPlatformDividedYuan() {
        return platformDividedYuan;
    }

    public void setPlatformDividedYuan(String platformDividedYuan) {
        this.platformDividedYuan = platformDividedYuan;
    }

    public String getAreaCommissionYuan() {
        return areaCommissionYuan;
    }

    public void setAreaCommissionYuan(String areaCommissionYuan) {
        this.areaCommissionYuan = areaCommissionYuan;
    }

    public String getStoreCommissionYuan() {
        return storeCommissionYuan;
    }

    public void setStoreCommissionYuan(String storeCommissionYuan) {
        this.storeCommissionYuan = storeCommissionYuan;
    }

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
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

