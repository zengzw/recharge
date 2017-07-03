package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PhoneReportVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String phoneOrderCode;

    private String openPlatformNo;
    private String supplierOrderId;

    private String supplierName;

    // 充值手机号
    private String memberMobile;

    private String payStatus;

    private String createTime;

    private String paySuccssTime;

    private String payUserName;

    // 会员手机号
    private String mobile;

    private String storeName;

    private String storeCode;

    private String storeNo;

    private String originalAmount;

    private String saleAmount;

    private String realAmount;

    private String costingAmount;

    private String status;

    private String praRealAmount;

    private String refundAmountCode;

    private String refundAmount;

    private String refundCode;

    private String areaCommissionYuan;

    private String storeCommissionYuan;

    private String startDate;

    private String endDate;

    private String province;
    private String city;
    private String countryName;
    private String statusStr;
    private String tdAmount;

    private Integer page;
    private Integer rows;

    private String platformDividedRatioStr;
    private String areaCommissionRatioStr;

    private String storeCommissionRatioStr;
    private String countryCode;
    private String rechargePhone;

    private String rechargeSuccssTime;

    private String platformDividedRatio;// '平台和县域，平台分成比',

    private String areaDividedRatio;// '平台和县域，县域分成比',

    private String areaCommissionRatio;// '县域和网点，县域佣金比',
    private String storeCommissionRatio;// '县域和网点，网点佣金比',
    private String shopName;
    private String refundStatus; // 退款状态

    private String platformMinnus;//平台优惠
    
    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    private String sources; //订单来源

    public String getAreaCommissionRatio() {
        return areaCommissionRatio;
    }

    public String getAreaCommissionRatioStr() {
        return areaCommissionRatioStr;
    }
    
    public String getProfit(){
        if(StringUtils.isEmpty(costingAmount)
                || StringUtils.isEmpty(originalAmount)){
            return "—";
        }
        
       BigDecimal costingDecimal =  BigDecimal.valueOf(Double.parseDouble(costingAmount));
       BigDecimal originalDecimal =  BigDecimal.valueOf(Double.parseDouble(originalAmount));
       return originalDecimal.subtract(costingDecimal).setScale(2).toString();
    }
    

    
    public String getAreaCommissionYuan() {
        return areaCommissionYuan;
    }

    public String getAreaDividedRatio() {
        return areaDividedRatio;
    }

    public String getCity() {
        return city;
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

    public String getEndDate() {
        return endDate;
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

    public Integer getPage() {
        return page;
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

    public String getPlatformDividedRatioStr() {
        return platformDividedRatioStr;
    }

    public String getPraRealAmount() {
        return praRealAmount;
    }

    public String getProvince() {
        return province;
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

    public String getRefundAmount() {
        return refundAmount;
    }

    public String getRefundAmountCode() {
        return refundAmountCode;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public Integer getRows() {
        return rows;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getStoreCommissionRatio() {
        return storeCommissionRatio;
    }

    public String getStoreCommissionRatioStr() {
        return storeCommissionRatioStr;
    }

    public String getStoreCommissionYuan() {
        return storeCommissionYuan;
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

    public String getTdAmount() {
        return tdAmount;
    }

    public void setAreaCommissionRatio(String areaCommissionRatio) {
        this.areaCommissionRatio = areaCommissionRatio;
    }

    public void setAreaCommissionRatioStr(String areaCommissionRatioStr) {
        this.areaCommissionRatioStr = areaCommissionRatioStr;
    }

    public void setAreaCommissionYuan(String areaCommissionYuan) {
        this.areaCommissionYuan = areaCommissionYuan;
    }

    public void setAreaDividedRatio(String areaDividedRatio) {
        this.areaDividedRatio = areaDividedRatio;
    }

    public void setCity(String city) {
        this.city = city;
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

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public void setPage(Integer page) {
        this.page = page;
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

    public void setPlatformDividedRatioStr(String platformDividedRatioStr) {
        this.platformDividedRatioStr = platformDividedRatioStr;
    }

    public void setPraRealAmount(String praRealAmount) {
        this.praRealAmount = praRealAmount;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setRefundAmountCode(String refundAmountCode) {
        this.refundAmountCode = refundAmountCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setStoreCommissionRatio(String storeCommissionRatio) {
        this.storeCommissionRatio = storeCommissionRatio;
    }

    public void setStoreCommissionRatioStr(String storeCommissionRatioStr) {
        this.storeCommissionRatioStr = storeCommissionRatioStr;
    }

    public void setStoreCommissionYuan(String storeCommissionYuan) {
        this.storeCommissionYuan = storeCommissionYuan;
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

    public void setTdAmount(String tdAmount) {
        this.tdAmount = tdAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getPlatformMinnus() {
        return platformMinnus;
    }

    public void setPlatformMinnus(String platformMinnus) {
        this.platformMinnus = platformMinnus;
    }
}
