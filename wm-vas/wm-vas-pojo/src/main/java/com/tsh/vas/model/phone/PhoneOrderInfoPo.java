package com.tsh.vas.model.phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "phone_order_info")
public class PhoneOrderInfoPo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -1259479571342981846L;


    /**  订单ID*/
    private Long id;

    /**  外部订单编号*/
    private String phoneOrderCode;


    /**
     * 开放平台订单编号
     */
    private String openPlatformNo;

    /**  供应商订单编号*/
    private String supplierOrderId;

    /**  供应商编号*/
    private String supplierCode;

    /**  供应商名称*/
    private String supplierName;
    /**  父级服务业务编号*/
    private String businessCode;
    /**  父级服务业务名称*/
    private String businessName;
    /**  子分类编号*/
    private String subBusinessCode;
    /**  子分类名称*/
    private String subBusinessName;
    /**  市*/
    private String city;

    /**  省*/
    private String province;
    /**  县*/
    private String country;

    /**  县运营中心编号ID*/
    private String countryCode;
    /**  县运营中心名称*/
    private String countryName;

    /**  会员手机号码*/
    private String memberMobile;
    /**  会员姓名*/
    private String memberName;

    /**  支付会员用户电话*/
    private String mobile;

    /**  支付会员用户姓名*/
    private String payUserName;

    /**  支付会员用户id*/
    private Long payUserId;

    /**  付费用户biz_id*/
    private Long bizId;

    /**  付费用户账户角色类型（2：平台管理，3：县域，4：网点，5：供应商，9：会员）*/
    private Integer bizType;

    /**  网点编号*/
    private String storeCode;
    
    /**
     * 网点编号
     */
    private String storeNo;

    /**  网点名称*/
    private String storeName;

    /**  供货价(供应商）*/
    private BigDecimal costingAmount;

    /** 销售价（平台) */
    private BigDecimal saleAmount;

    /**  应付金额*/
    private BigDecimal originalAmount;

    /**  实付金额*/
    private BigDecimal realAmount;

    /**  创建时间*/
    private Date createTime;

    /**  支付成功时间*/
    private Date paySuccssTime;

    /**  订单状态（1：待支付，2：支付中，3：购票中，4：出票中，5：交易成功，6：支付失败，7：购票失败/待退款，8：出票失败/待退款，16：支付异常/待退款）*/
    private Integer payStatus;

    /**  网点其他信息*/
    private String storeInfo;

    /**  账期*/
    private String billYearMonth;

    /**  平台和县域，平台分成比*/
    private double platformDividedRatio;

    /**  平台和县域，县域分成比*/
    private double areaDividedRatio;

    /**  县域和网点，县域佣金比*/
    private double areaCommissionRatio;

    /**  县域和网点，网点佣金比*/
    private double storeCommissionRatio;

    /**  供应商产品id*/
    private String goodsId;

    /**  联系人手机号*/
    private String rechargePhone;
    
    
    /**
     * 充值成功时间
     */
    private Date rechargeSuccessTime;
    

    private String linkName;    //购票联系人姓名
    
    
    private String linkPhone;   //购票联系人电话
    
    private Integer activetyMoeny; //参与活动金额
    
    /**
     * 订单来源
     */
    private String sources;
    
    
    /**
     * 微信用户Id
     */
    private String openUserId;
    
    /**
     * 充值手机号码会员Id
     */
    private String phoneMemberId;
    
    
    /**
     * 描述
     */
    private String remark;
    
    
    /**
     * 订单优惠券信息
     */
    private PhoneUseCouponRecordPo orderCoupon;
    
    /**
     * 参加活动状态
     */
    private Integer joinStatus;

    @Transient
    public Integer getActivetyMoeny() {
        return activetyMoeny;
    }

    @Column(name = "area_commission_ratio")
    public double getAreaCommissionRatio() {
        return areaCommissionRatio;
    }

    @Column(name = "area_divided_ratio")
    public double getAreaDividedRatio() {
        return areaDividedRatio;
    }

    @Column(name = "bill_year_month")
    public String getBillYearMonth() {
        return billYearMonth;
    }
    
    
    @Column(name = "biz_id")
    public Long getBizId() {
        return bizId;
    }

    @Column(name = "biz_type")
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = "business_code")
    public String getBusinessCode() {
        return businessCode;
    }

    @Column(name = "business_name")
    public String getBusinessName() {
        return businessName;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    @Column(name = "costing_amount")
    public BigDecimal getCostingAmount() {
        return costingAmount;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }
    
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }
    @Column(name = "goods_id")
    public String getGoodsId() {
        return goodsId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    @Transient
    public Integer getJoinStatus() {
        return joinStatus;
    }
    @Column(name = "link_name")
    public String getLinkName() {
        return linkName;
    }
    
    @Column(name = "link_phone")
    public String getLinkPhone() {
        return linkPhone;
    }

    @Column(name = "member_mobile")
    public String getMemberMobile() {
        return memberMobile;
    }

    @Column(name = "member_name")
    public String getMemberName() {
        return memberName;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    @Column(name="open_platform_no")
    public String getOpenPlatformNo() {
        return openPlatformNo;
    }

    @Column(name="open_user_id")
    public String getOpenUserId() {
        return openUserId;
    }

    @Transient
    public PhoneUseCouponRecordPo getOrderCoupon() {
        return orderCoupon;
    }

    @Column(name = "original_amount")
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    @Column(name = "pay_status")
    public Integer getPayStatus() {
        return payStatus;
    }

    @Column(name = "pay_succss_time")
    public Date getPaySuccssTime() {
        return paySuccssTime;
    }

    @Column(name = "pay_user_id")
    public Long getPayUserId() {
        return payUserId;
    }

    @Column(name = "pay_user_name")
    public String getPayUserName() {
        return payUserName;
    }

    @Column(name="phone_member_id")
    public String getPhoneMemberId() {
        return phoneMemberId;
    }

    @Column(name = "phone_order_code")
    public String getPhoneOrderCode() {
        return phoneOrderCode;
    }

    @Column(name = "platform_divided_ratio")
    public double getPlatformDividedRatio() {
        return platformDividedRatio;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }
    @Column(name = "real_amount")
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    @Column(name = "recharge_phone")
    public String getRechargePhone() {
        return rechargePhone;
    }
    @Column(name="recharge_success_time")
    public Date getRechargeSuccessTime() {
        return rechargeSuccessTime;
    }

    @Column(name="remark")
    public String getRemark() {
        return remark;
    }
    @Column(name = "sale_amount")
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    @Column(name = "sources")
    public String getSources() {
        return sources;
    }
    @Column(name = "store_code")
    public String getStoreCode() {
        return storeCode;
    }

    @Column(name = "store_commission_ratio")
    public double getStoreCommissionRatio() {
        return storeCommissionRatio;
    }
    @Column(name = "store_info")
    public String getStoreInfo() {
        return storeInfo;
    }

    @Column(name = "store_name")
    public String getStoreName() {
        return storeName;
    }
    @Column(name="store_no")
    public String getStoreNo() {
        return storeNo;
    }

    @Column(name = "sub_business_code")
    public String getSubBusinessCode() {
        return subBusinessCode;
    }
    @Column(name = "sub_business_name")
    public String getSubBusinessName() {
        return subBusinessName;
    }

    @Column(name = "supplier_code")
    public String getSupplierCode() {
        return supplierCode;
    }
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    @Column(name = "supplier_order_id")
    public String getSupplierOrderId() {
        return supplierOrderId;
    }
    public void setActivetyMoeny(Integer activetyMoeny) {
        this.activetyMoeny = activetyMoeny;
    }

    public void setAreaCommissionRatio(double areaCommissionRatio) {
        this.areaCommissionRatio =areaCommissionRatio;
    }
    public void setAreaDividedRatio(double areaDividedRatio) {
        this.areaDividedRatio =areaDividedRatio;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth =billYearMonth;
    }
    public void setBizId(Long bizId) {
        this.bizId =bizId;
    }

    public void setBizType(Integer bizType) {
        this.bizType =bizType;
    }
    public void setBusinessCode(String businessCode) {
        this.businessCode =businessCode;
    }

    public void setBusinessName(String businessName) {
        this.businessName =businessName;
    }
    public void setCity(String city) {
        this.city =city;
    }

    public void setCostingAmount(BigDecimal costingAmount) {
        this.costingAmount =costingAmount;
    }
    public void setCountry(String country) {
        this.country =country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode =countryCode;
    }
    public void setCountryName(String countryName) {
        this.countryName =countryName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId =goodsId;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile =memberMobile;
    }
    public void setMemberName(String memberName) {
        this.memberName =memberName;
    }

    public void setMobile(String mobile) {
        this.mobile =mobile;
    }
    public void setOpenPlatformNo(String openPlatformNo) {
        this.openPlatformNo = openPlatformNo;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }
    public void setOrderCoupon(PhoneUseCouponRecordPo orderCoupon) {
        this.orderCoupon = orderCoupon;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount =originalAmount;
    }
    public void setPayStatus(Integer payStatus) {
        this.payStatus =payStatus;
    }

    public void setPaySuccssTime(Date paySuccssTime) {
        this.paySuccssTime =paySuccssTime;
    }
    public void setPayUserId(Long payUserId) {
        this.payUserId =payUserId;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName =payUserName;
    }
    public void setPhoneMemberId(String phoneMemberId) {
        this.phoneMemberId = phoneMemberId;
    }

    public void setPhoneOrderCode(String phoneOrderCode) {
        this.phoneOrderCode =phoneOrderCode;
    }
    public void setPlatformDividedRatio(double platformDividedRatio) {
        this.platformDividedRatio =platformDividedRatio;
    }

    public void setProvince(String province) {
        this.province =province;
    }
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount =realAmount;
    }

    public void setRechargePhone(String rechargePhone) {
        this.rechargePhone =rechargePhone;
    }
    public void setRechargeSuccessTime(Date rechargeSuccessTime) {
        this.rechargeSuccessTime = rechargeSuccessTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount =saleAmount;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }
    public void setStoreCode(String storeCode) {
        this.storeCode =storeCode;
    }

    public void setStoreCommissionRatio(double storeCommissionRatio) {
        this.storeCommissionRatio =storeCommissionRatio;
    }
    public void setStoreInfo(String storeInfo) {
        this.storeInfo =storeInfo;
    }

    public void setStoreName(String storeName) {
        this.storeName =storeName;
    }
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public void setSubBusinessCode(String subBusinessCode) {
        this.subBusinessCode =subBusinessCode;
    }
    public void setSubBusinessName(String subBusinessName) {
        this.subBusinessName =subBusinessName;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode =supplierCode;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName =supplierName;
    }

    public void setSupplierOrderId(String supplierOrderId) {
        this.supplierOrderId =supplierOrderId;
    }
}
