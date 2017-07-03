package com.tsh.vas.vo.charge;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by Iritchie.ren on 2016/10/8.
 */
public class ChargeInfoVo {

    /**
     * 业务编码
     */
    private String businessCode;
    /**
     * 缴费机构编码
     */
    private String chargeOrgCode;
    /**
     * 缴费机构
     */
    private String chargeOrgName;
    /**
     * 用户手机号码
     */
    private String mobile;
    /**
     * 应缴金额
     */
    private BigDecimal originalAmount;
    /**
     * 实缴金额
     */
    private BigDecimal realAmount;
    /**
     * 成本价
     */
    private BigDecimal costingAmount;
    /**
     * 缴费用户账号
     */
    private String rechargeUserCode;
    /**
     * 缴费用户账号类型，付费用户账户角色类型biz_type：角色类型 2:平台管理 3:县域 4:网点，5：供应商 9：会员）
     */
    private Integer bizType;
    /**
     * 缴费用户姓名
     */
    private String rechargeUserName;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 开放平台供应商秘钥
     */
    private String supplierToken;
    /**
     * 供应商服务器地址
     */
    private String serverAddr;
    /**
     * 账单日期YYYYMM.
     */
    private String billYearMonth;
    /**
     * 缴费账户类型，缴费账户类型 1.户号 2.条形码
     */
    private Integer rechargeUserType;
    /**
     * 供应商，产品ID
     */
    private String productId;

    /**
     * 充值扩展字段
     */
    private String extContent; //充值扩展字段，Map<String,String> 对象json字符串格式

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public BigDecimal getCostingAmount() {
        return costingAmount;
    }

    public void setCostingAmount(BigDecimal costingAmount) {
        this.costingAmount = costingAmount;
    }

    public String getRechargeUserCode() {
        return rechargeUserCode;
    }

    public void setRechargeUserCode(String rechargeUserCode) {
        this.rechargeUserCode = rechargeUserCode;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getRechargeUserName() {
        return rechargeUserName;
    }

    public void setRechargeUserName(String rechargeUserName) {
        this.rechargeUserName = rechargeUserName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getSupplierToken() {
        return supplierToken;
    }

    public void setSupplierToken(String supplierToken) {
        this.supplierToken = supplierToken;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getBillYearMonth() {
        return billYearMonth;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth = billYearMonth;
    }

    public Integer getRechargeUserType() {
        return rechargeUserType;
    }

    public void setRechargeUserType(Integer rechargeUserType) {
        this.rechargeUserType = rechargeUserType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
