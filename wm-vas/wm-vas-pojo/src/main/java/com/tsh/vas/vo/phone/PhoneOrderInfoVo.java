package com.tsh.vas.vo.phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dtds.platform.commons.utility.DateUtil;


public class PhoneOrderInfoVo extends BaseVO implements Serializable {

    /**
     * 
     */
    private static final Long serialVersionUID = -8330912968534051991L;


    /**  火车票订单ID*/
    private Long id;
    /**  火车票订单编号*/
    private String phoneOrderCode;

    /**  外部订单编号*/
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

    /**  供货价*/
    private BigDecimal costingAmount;

    /** 面额 */
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

    /**  充值手机手机号*/
    private String rechargePhone;


    /**
     * 充值成功时间
     */
    private Date rechargeSuccessTime;

    /**
     * 用户姓名
     */
    private String userName;


    /**
     * 支付密码
     */
    private String payPassword;


    /**
     * 当前爷
     */
    private int page;


    /**
     * 行数
     */
    private int rows;

    private String statusIn;//订单状态


    private String searchInfo;//查询信息。重置手机号码，订单号，联系人


    private String payStatusStr; //支付状态

    /**
     * 退款状态
     */
    private String refundStatusStr;
    
    /**
     * 运营商名称
     */
    private String spName;
    
    
    private String linkName;
    
    
    private String linkPhone;
    
    
    /**
     * 参加状态 0:不参与，1：参与
     */
    private Integer joinStatus;
    
    /**
     * 参加活动金额
     */
    private Integer joinMoney;
    
    
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
     * 网点佣金
     */
    private String storeCommissionRatioStr;
    
    
    /**
     * 优惠券Id
     */
    private Integer couponId;
    
    

    private BigDecimal discountAmount;

    public double getAreaCommissionRatio() {
        return areaCommissionRatio;
    }
    public double getAreaDividedRatio() {
        return areaDividedRatio;
    }

    public String getBillYearMonth() {
        return billYearMonth;
    }

    public Long getBizId() {
        return bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getCity() {
        return city;
    }

    public BigDecimal getCostingAmount() {
        return costingAmount;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    /**
     * date 字符串
     * @return
     */
    public String getCreateTimeStr(){
        if(createTime != null){
            return DateUtil.date2String(createTime);
        }

        return "";
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public Long getId() {
        return id;
    }

    public Integer getJoinMoney() {
        return joinMoney;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOpenPlatformNo() {
        return openPlatformNo;
    }


    public String getOpenUserId() {
        return openUserId;
    }


    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }


    public int getPage() {
        return page;
    }



    public String getPayPassword() {
        return payPassword;
    }



    public Integer getPayStatus() {
        return payStatus;
    }


    public String getPayStatusStr() {
        return payStatusStr;
    }

    public Date getPaySuccssTime() {
        return paySuccssTime;
    }


    public Long getPayUserId() {
        return payUserId;
    }


    public String getPayUserName() {
        return payUserName;
    }


    public String getPhoneMemberId() {
        return phoneMemberId;
    }

    public String getPhoneOrderCode() {
        return phoneOrderCode;
    }



    public double getPlatformDividedRatio() {
        return platformDividedRatio;
    }


    public String getProvince() {
        return province;
    }


    public BigDecimal getRealAmount() {
        return realAmount;
    }


    public String getRechargePhone() {
        return rechargePhone;
    }


    public Date getRechargeSuccessTime() {
        return rechargeSuccessTime;
    }


    /**
     * 充值成功时间
     * 
     * @return
     */
    public  String getRechargeSuccessTimeStr(){
        if(this.getRechargeSuccessTime() != null){
            return DateUtil.date2String(getRechargeSuccessTime());
        }

        return "-";
    }



    public String getRefundStatusStr() {
        return refundStatusStr;
    }

    public String getRemark() {
        return remark;
    }

    public int getRows() {
        return rows;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public String getSources() {
        return sources;
    }

    public String getSpName() {
        return spName;
    }

    public String getStatusIn() {
        return statusIn;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public double getStoreCommissionRatio() {
        return storeCommissionRatio;
    }

    public String getStoreCommissionRatioStr() {
        return storeCommissionRatioStr;
    }

    public String getStoreInfo() {
        return storeInfo;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreNo() {
        return storeNo;
    }




    public String getSubBusinessCode() {
        return subBusinessCode;
    }

    public String getSubBusinessName() {
        return subBusinessName;
    }
    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }
    public String getSupplierOrderId() {
        return supplierOrderId;
    }

    public String getUserName() {
        return userName;
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
        this.businessCode = businessCode;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }
    public void setCreateTime(Date createTime) {
        this.createTime =createTime;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId =goodsId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setJoinMoney(Integer joinMoney) {
        this.joinMoney = joinMoney;
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
    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount =originalAmount;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus =payStatus;
    }
    public void setPayStatusStr(String payStatusStr) {
        this.payStatusStr = payStatusStr;
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

    public void setRefundStatusStr(String refundStatusStr) {
        this.refundStatusStr = refundStatusStr;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount =saleAmount;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }
    public void setSources(String sources) {
        this.sources = sources;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public void setStatusIn(String statusIn) {
        this.statusIn = statusIn;
    }
    public void setStoreCode(String storeCode) {
        this.storeCode =storeCode;
    }

    public void setStoreCommissionRatio(double storeCommissionRatio) {
        this.storeCommissionRatio =storeCommissionRatio;
    }
    public void setStoreCommissionRatioStr(String storeCommissionRatioStr) {
        this.storeCommissionRatioStr = storeCommissionRatioStr;
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
        this.subBusinessCode = subBusinessCode;
    }

    public void setSubBusinessName(String subBusinessName) {
        this.subBusinessName = subBusinessName;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
