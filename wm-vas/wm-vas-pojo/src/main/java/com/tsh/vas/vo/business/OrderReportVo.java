package com.tsh.vas.vo.business;

import com.dtds.platform.commons.utility.DateUtil;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderReportVo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
   
    private Long id;
    
    private String chargeCode = "";
    
    private String openPlatformNo = "";
   
    private String supplierCode = "";
   
    private String supplierName = "";
   
    private String businessCode = "";
    
    private String businessName = "";
   
    private String chargeOrgCode = "";
    
    private String chargeOrgName = "";
    
    private String rechargeUserCode = "";
   
    private String rechargeUserName = "";
    
    private String province = "";
    
    private String city = "";
    
    private String country = "";
    
    private String countryCode = "";
    
    private String countryName = "";
    
    private String rechargeUserAddr = "";
   
    private String mobile = "";
    
    private Integer payUserType = 0;
    
    private Long payUserId = 0L;
    
    private String storeCode = "";
    
    private String storeName = "";
    
    private BigDecimal costingAmount = new BigDecimal (0);
    
    private BigDecimal originalAmount = new BigDecimal (0);
   
    private BigDecimal realAmount = new BigDecimal (0);
    
    private Date createTime;
    
    private Integer refundStatus = 0;
    
    private String refundStatusStr = ""; //退款状态字符串
    
    private Integer payStatus = 0;
    
    private String payStatusStr = ""; //支付状态字符串
    
    private String storeInfo = "";
   
    private String subBusinessCode = "";    //子分类编号
   
    private String subBusinessName = ""; //子分类名
  
    private Double platformDividedRatio = 0.00; //平台和县域，平台分成
    
    private Double areaDividedRatio = 0.00; //平台和县域，县域分成比例
    
    private Double areaCommissionRatio = 0.00; //县域和网点，县域分成比例
    
    private Double storeCommissionRatio = 0.00; //县域和网点，网点分成比例
   
    private String liftCoefficient = ""; //提点系数
    
    private String paymentAccount = ""; //充值缴费账号(话费存电话号码，水电煤气费存户号)
    
    private String payUserName; //充值缴费联系人姓名
    
    
    private Double refundAmount;
    
   
    private String refundCode;
    private String memberMobile;
    private String memberName;
    
    private String platformDividedYuan;
    private String areaCommissionYuan;
    private String storeCommissionYuan;
    
    
    
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

	public String getRefundCode() {
		return refundCode;
	}

	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCreateTimeStr() {
        return DateUtil.date2String (createTime);
    }

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

    public String getOpenPlatformNo() {
        return openPlatformNo;
    }

    public void setOpenPlatformNo(String openPlatformNo) {
        this.openPlatformNo = openPlatformNo;
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

    public String getRechargeUserAddr() {
        return rechargeUserAddr;
    }

    public void setRechargeUserAddr(String rechargeUserAddr) {
        this.rechargeUserAddr = rechargeUserAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPayUserType() {
        return payUserType;
    }

    public void setPayUserType(Integer payUserType) {
        this.payUserType = payUserType;
    }

    public Long getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(Long payUserId) {
        this.payUserId = payUserId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
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

    public String getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(String storeInfo) {
        this.storeInfo = storeInfo;
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

	public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

	public String getRefundStatusStr() {
		return refundStatusStr;
	}

	public void setRefundStatusStr(String refundStatusStr) {
		this.refundStatusStr = refundStatusStr;
	}

	public String getPayStatusStr() {
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	public String getPayUserName() {
		return payUserName;
	}

	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
