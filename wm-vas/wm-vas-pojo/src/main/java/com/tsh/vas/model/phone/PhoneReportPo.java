package com.tsh.vas.model.phone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SuppressWarnings(value="all")
public class PhoneReportPo implements Serializable{
	
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "phone_order_code")
    private String phoneOrderCode;
    
    @Column(name="open_platform_no")
    private String openPlatformNo;

    @Column(name = "supplier_order_id")
    private String supplierOrderId;


    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "member_mobile")
    private String memberMobile;
    
    @Column(name = "pay_status")
    private String payStatus;
    
    @Column(name = "create_time")
    private String createTime;
    
    @Column(name = "pay_succss_time")
    private String paySuccssTime;
    
    @Column(name = "pay_user_name")
    private String payUserName;
    
    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "store_name")
    private String storeName;
    
    @Column(name = "store_code")
	private String storeCode;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_code")
	private String countryCode;

    @Column(name = "store_no")
	private String storeNo;

	@Column(name = "original_amount")
    private String originalAmount;

	@Column(name = "real_amount")
    private String realAmount;

    @Column(name = "costing_amount")
    private String costingAmount;
    
    @Column(name = "sale_amount")
    private String saleAmount;
    
    @Column(name = "status")
    private String status;

    @Column(name = "pra_real_amount")
    private String praRealAmount;
    
    @Column(name = "refund_amount_code")
    private String refundAmountCode;
    
    @Column(name = "platform_divided_ratio")
    private String platformDividedRatio;
    
    @Column(name = "area_divided_ratio")
    private String areaDividedRatio;
    
    @Column(name = "area_commission_ratio")
    private String areaCommissionRatio;
    
    @Column(name = "store_commission_ratio")
    private String storeCommissionRatio;
    
    @Column(name = "recharge_phone")
    private String rechargePhone;
    
    @Column(name = "recharge_success_time")
    private String rechargeSuccssTime;

    @Column(name="sources")
    private String sources;
    
	public String getAreaCommissionRatio() {
		return areaCommissionRatio;
	}

    public String getAreaDividedRatio() {
		return areaDividedRatio;
	}

    public String getCostingAmount() {
		return costingAmount;
	}

    public String getCountryCode() {
		return countryCode;
	}

    public String getCountryName() {
		return countryName;
	}


	public String getCreateTime() {
		return createTime;
	}


	public Long getId() {
		return id;
	}


	public String getMemberMobile() {
		return memberMobile;
	}


	public String getMobile() {
		return mobile;
	}


	public String getOpenPlatformNo() {
        return openPlatformNo;
    }


	public String getOriginalAmount() {
		return originalAmount;
	}


	public String getPayStatus() {
		return payStatus;
	}


	public String getPaySuccssTime() {
		return paySuccssTime;
	}


	public String getPayUserName() {
		return payUserName;
	}


	public String getPhoneOrderCode() {
		return phoneOrderCode;
	}


	public String getPlatformDividedRatio() {
		return platformDividedRatio;
	}


	


	public String getPraRealAmount() {
		return praRealAmount;
	}


	public String getRealAmount() {
		return realAmount;
	}


	public String getRechargePhone() {
		return rechargePhone;
	}


	public String getRechargeSuccssTime() {
        return rechargeSuccssTime;
    }


	public String getRefundAmountCode() {
		return refundAmountCode;
	}


	public String getSaleAmount() {
		return saleAmount;
	}


	public String getSources() {
        return sources;
    }


	public String getStatus() {
		return status;
	}


	public String getStoreCode() {
		return storeCode;
	}


	public String getStoreCommissionRatio() {
		return storeCommissionRatio;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public String getSupplierName() {
		return supplierName;
	}


	public String getSupplierOrderId() {
		return supplierOrderId;
	}


	public void setAreaCommissionRatio(String areaCommissionRatio) {
		this.areaCommissionRatio = areaCommissionRatio;
	}


	public void setAreaDividedRatio(String areaDividedRatio) {
		this.areaDividedRatio = areaDividedRatio;
	}


	public void setCostingAmount(String costingAmount) {
		this.costingAmount = costingAmount;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public void setOpenPlatformNo(String openPlatformNo) {
        this.openPlatformNo = openPlatformNo;
    }


	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}


	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}


	public void setPaySuccssTime(String paySuccssTime) {
		this.paySuccssTime = paySuccssTime;
	}


	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}


	public void setPhoneOrderCode(String phoneOrderCode) {
		this.phoneOrderCode = phoneOrderCode;
	}


	public void setPlatformDividedRatio(String platformDividedRatio) {
		this.platformDividedRatio = platformDividedRatio;
	}


	public void setPraRealAmount(String praRealAmount) {
		this.praRealAmount = praRealAmount;
	}


	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}

	public void setRechargePhone(String rechargePhone) {
		this.rechargePhone = rechargePhone;
	}

	public void setRechargeSuccssTime(String rechargeSuccssTime) {
        this.rechargeSuccssTime = rechargeSuccssTime;
    }

	public void setRefundAmountCode(String refundAmountCode) {
		this.refundAmountCode = refundAmountCode;
	}

	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}

    public void setSources(String sources) {
        this.sources = sources;
    }

    public void setStatus(String status) {
		this.status = status;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public void setStoreCommissionRatio(String storeCommissionRatio) {
		this.storeCommissionRatio = storeCommissionRatio;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}

