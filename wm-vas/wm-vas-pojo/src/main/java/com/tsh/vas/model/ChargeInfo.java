package com.tsh.vas.model;

import com.dtds.platform.commons.utility.DateUtil;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.enume.RoleType;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Entity
@Table(name = "charge_info")
public class ChargeInfo implements Serializable {

    /**
     * 缴费单信息id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 缴费订单编号
     */
    @Basic
    @Column(name = "charge_code")
    private String chargeCode = "";
    /**
     * 开放平台的订单编号
     */
    @Basic
    @Column(name = "open_platform_no")
    private String openPlatformNo = "";
    /**
     * 供应商编
     */
    @Basic
    @Column(name = "supplier_code")
    private String supplierCode = "";
    /**
     * 供应商名
     */
    @Basic
    @Column(name = "supplier_name")
    private String supplierName = "";
    /**
     * 服务业务编号
     */
    @Basic
    @Column(name = "business_code")
    private String businessCode = "";
    /**
     * 服务业务名称
     */
    @Basic
    @Column(name = "business_name")
    private String businessName = "";
    /**
     * 收费机构编号
     */
    @Basic
    @Column(name = "charge_org_code")
    private String chargeOrgCode = "";
    /**
     * 收费机构名称
     */
    @Basic
    @Column(name = "charge_org_name")
    private String chargeOrgName = "";
    /**
     * 缴费账户编号，户号
     */
    @Basic
    @Column(name = "recharge_user_code")
    private String rechargeUserCode = "";
    /**
     * 缴费账户用户名称
     */
    @Basic
    @Column(name = "recharge_user_name")
    private String rechargeUserName = "";
    /**
     * 省
     */
    @Basic
    @Column(name = "province")
    private String province = "";
    /**
     * 市
     */
    @Basic
    @Column(name = "city")
    private String city = "";
    /**
     * 县
     */
    @Basic
    @Column(name = "country")
    private String country = "";
    /**
     * 县运营中心编号id
     */
    @Basic
    @Column(name = "country_code")
    private String countryCode = "";
    /**
     * 县运营中心名
     */
    @Basic
    @Column(name = "country_name")
    private String countryName = "";
    /**
     * 付费用户地址
     */
    @Basic
    @Column(name = "recharge_user_addr")
    private String rechargeUserAddr = "";
    /**
     * 会员用户电话
     */
    @Basic
    @Column(name = "mobile")
    private String mobile = "";

    /**
     * 会员用户id
     */
    @Basic
    @Column(name = "pay_user_id")
    private Long payUserId = 0L;

    /**
     * 付费用户业务id
     */
    @Basic
    @Column(name = "biz_id")
    private Long bizId = 0L;
    /**
     * 付费用户业务类型
     */
    @Basic
    @Column(name = "biz_type")
    private Integer bizType = 0;
    /**
     * 网点编号id
     */
    @Basic
    @Column(name = "store_code")
    private String storeCode = "";
    /**
     * 网点名称
     */
    @Basic
    @Column(name = "store_name")
    private String storeName = "";
    /**
     * 成本
     */
    @Basic
    @Column(name = "costing_amount")
    private BigDecimal costingAmount = new BigDecimal (0);
    /**
     * 应缴金额
     */
    @Basic
    @Column(name = "original_amount")
    private BigDecimal originalAmount = new BigDecimal (0);
    /**
     * 实缴金额
     */
    @Basic
    @Column(name = "real_amount")
    private BigDecimal realAmount = new BigDecimal (0);
    /**
     * 退款状态：0.初始状态，未发生退款，1.退款中 .已退款，3，退款失
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "refund_status")
    private Integer refundStatus = 0;
    /**
     * 支付状态：1：待支付：支付中：缴费中：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销
     */
    @Basic
    @Column(name = "pay_status")
    private Integer payStatus = 0;
    /**
     * 网点其他信息
     */
    @Basic
    @Column(name = "store_info")
    private String storeInfo = "";
    /**
     * 子分类编号
     */
    @Basic
    @Column(name = "sub_business_code")
    private String subBusinessCode = "";
    /**
     * 子分类名
     */
    @Basic
    @Column(name = "sub_business_name")
    private String subBusinessName = "";
    /**
     * 提点系数
     */
    @Basic
    @Column(name = "lift_coefficient")
    private Double liftCoefficient = 0.00;
    /**
     * 平台和县域，平台分成
     */
    @Basic
    @Column(name = "platform_divided_ratio")
    private Double platformDividedRatio = 0.00;
    /**
     * 平台和县域，县域分成比例
     */
    @Basic
    @Column(name = "area_divided_ratio")
    private Double areaDividedRatio = 0.00;
    /**
     * 县域和网点，县域分成比例
     */
    @Basic
    @Column(name = "area_commission_ratio")
    private Double areaCommissionRatio = 0.00;
    /**
     * 县域和网点，网点分成比例
     */
    @Basic
    @Column(name = "store_commission_ratio")
    private Double storeCommissionRatio = 0.00;
    /**
     * 缴费账单日期YYYYMM。
     */
    @Basic
    @Column(name = "bill_year_month")
    private String billYearMonth = "";
    /**
     * 缴费账户类型 1.户号 2.条形码
     */
    @Basic
    @Column(name = "recharge_user_type")
    private Integer rechargeUserType = 0;
    /**
     * 会员用户姓名
     */
    @Basic
    @Column(name = "pay_user_name")
    private String payUserName = "";
    /**
     * 供应商缴费业务产品id
     */
    @Basic
    @Column(name = "product_id")
    private String productId = "";
    /**
     * 会员手机号码
     */
    @Basic
    @Column(name = "member_mobile")
    private String memberMobile = "";
    /**
     * 会员姓名
     */
    @Basic
    @Column(name = "member_name")
    private String memberName = "";
    /**
     * 充值扩展字段
     */
    @Basic
    @Column(name = "ext_content")
    private String extContent = ""; //充值扩展字段，Map<String,String> 对象json字符串格式

    /**
     * 退款状态名归类
     */
    public String getRefundStatusName() {
        return ChargeRefundStatus.getEnume (refundStatus).getClientDesc ();
    }

    /**
     * 支付状态名归类
     */
    public String getPayStatusName() {
        return ChargePayStatus.getEnume (payStatus).getClientDesc ();
    }

    /**
     * 支付账户类型名称
     */
    public String getBizTypeName() {
        return RoleType.getEnume (bizType).getDesc ();
    }

    /**
     * 退款状态名
     */
    public String getRefundStatusStr() {
        return ChargeRefundStatus.getEnume (refundStatus).getDesc ();
    }

    /**
     * 支付状态名
     */
    public String getPayStatusStr() {
        return ChargePayStatus.getEnume (payStatus).getDesc ();
    }

    /**
     * 返回相应的时间格式
     *
     * @return
     */
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

    public Long getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(Long payUserId) {
        this.payUserId = payUserId;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
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

    public Double getLiftCoefficient() {
        return liftCoefficient;
    }

    public void setLiftCoefficient(Double liftCoefficient) {
        this.liftCoefficient = liftCoefficient;
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

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
