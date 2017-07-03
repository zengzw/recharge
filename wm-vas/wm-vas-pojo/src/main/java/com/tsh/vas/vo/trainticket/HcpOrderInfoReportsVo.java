package com.tsh.vas.vo.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单报表Po
 * 
 * @version 1.0.0 2016年11月29日<br>
 * @see
 * @since JDK 1.7.0
 */
public class HcpOrderInfoReportsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 火车票订单ID */
	private Integer id;
	/** 火车票订单编号 */
	private String hcpOrderCode;
	/** 开发平台订单编号 */
	private String openPlatformNo;
	/** 供应商编号 */
	private String supplierCode;
	/** 供应商名称 */
	private String supplierName;
	/** 父级服务业务编号 */
	private String businessCode;
	/** 父级服务业务名称 */
	private String businessName;
	/** 子分类编号 */
	private String subBusinessCode;
	/** 子分类名称 */
	private String subBusinessName;
	/** 市 */
	private String city;
	/** 省 */
	private String province;
	/** 县 */
	private String country;
	/** 县运营中心编号ID */
	private String countryCode;
	/** 县运营中心名称 */
	private String countryName;
	/** 支付会员用户电话 */
	private String mobile;
	/** 支付会员用户姓名 */
	private String payUserName;
	/** 支付会员用户id */
	private Integer payUserId;
	/** 付费用户biz_id */
	private Integer bizId;
	/** 付费用户账户角色类型（2：平台管理，3：县域，4：网点，5：供应商，9：会员） */
	private Integer bizType;
	/** 网点编号 */
	private String storeCode;
	/** 网点名称 */
	private String storeName;
	/** 成本价 */
	private BigDecimal costingAmount;
	/** 应付金额 */
	private BigDecimal originalAmount;
	/** 实付金额 */
	private BigDecimal realAmount;
	/** 创建时间 */
	private Date createTime;
	// 开始时间
	private String startDate;
	// 结束时间
	private String endDate;
	private String cpStartDate; //出票开始时间
	private String cpEndDate; //出票结束时间
	private String sqStartDate; //申请退票开始时间
	private String sqEndDate; //申请退票结束时间
	/** 支付状态（1：待支付，2：支付中，3：网点支付完成，4：交易成功，5：交易失败，6：网点支付异常 */
	private Integer payStatus;
	/** 网点其他信息 */
	private String storeInfo;
	/** 提点系数 */
	private Double liftCoefficient;
	/** 平台和县域，平台分成比 */
	private Double platformDividedRatio;
	/** 平台和县域，县域分成比 */
	private Double areaDividedRatio;
	/** 县域和网点，县域佣金比 */
	private Double areaCommissionRatio;
	/** 县域和网点，网点佣金比 */
	private Double storeCommissionRatio;
	/** 缴费账单日期 */
	private String billYearMonth;
	/** 供应商产品id */
	private String productId;
	/** 会员手机号码 */
	private String memberMobile;
	/** 会员姓名 */
	private String memberName;
	/** 乘车日期 */
	private String travelTime;
	/** 火车开车时间 */
	private String stationStartTime;
	/** 始发站 */
	private String fromStation;
	/** 火车到达时间 */
	private String stationArriveTime;
	/** 终点站 */
	private String arriveStation;
	/** 是否购买保险 */
	private Integer bx;
	/** 证件类型 */
	private Integer idType;
	/** 证件号码 */
	private String idCardId;
	/** 乘客姓名 */
	private String userName;
	/** 座位类型 */
	private Integer seatType;
	/** 车票价格 */
	private Double ticketPrice;
	/** 车次 */
	private String trainCode;
	/** 服务费用 */
	private Double servicePrice;
	/** 联系人姓名 */
	private String linkName;
	/** 联系人手机号 */
	private String linkPhone;
	/** 退票订单编号 */
	private String hcpRefundCode;
	/** 退款类型(1：购票退款，2：退票退款) */
	private Integer refundType;
	/** 退款金额 */
	private Double refundAmount;
	/** 支付方式 */
	private Integer payWay;

	/** 操作用户编号 */
	private String userCode;

	/** 操作用户手机号 */
	private String userMobile;
	/** 退款次数 */
	private Integer refundTimes;
	/** 退款时间 */
	private Date refundTime;
	/** 退款描述 */
	private String refundDesc;
	/** 退款状态 */
	private Integer status;
	/** 备注 */
	private String remark;
	
	private Integer page;
	private Integer rows;
	private String supplierOrderId; //第三方返回的订单号
	
	private Integer ticketType; //票种 (0-成人票)
	
	
	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHcpOrderCode() {
		return hcpOrderCode;
	}

	public void setHcpOrderCode(String hcpOrderCode) {
		this.hcpOrderCode = hcpOrderCode;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public String getPayUserName() {
		return payUserName;
	}

	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}

	public Integer getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(Integer payUserId) {
		this.payUserId = payUserId;
	}

	public Integer getBizId() {
		return bizId;
	}

	public void setBizId(Integer bizId) {
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

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getStationStartTime() {
		return stationStartTime;
	}

	public void setStationStartTime(String stationStartTime) {
		this.stationStartTime = stationStartTime;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getStationArriveTime() {
		return stationArriveTime;
	}

	public void setStationArriveTime(String stationArriveTime) {
		this.stationArriveTime = stationArriveTime;
	}

	public String getArriveStation() {
		return arriveStation;
	}

	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}

	public Integer getBx() {
		return bx;
	}

	public void setBx(Integer bx) {
		this.bx = bx;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdCardId() {
		return idCardId;
	}

	public void setIdCardId(String idCardId) {
		this.idCardId = idCardId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSeatType() {
		return seatType;
	}

	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getHcpRefundCode() {
		return hcpRefundCode;
	}

	public void setHcpRefundCode(String hcpRefundCode) {
		this.hcpRefundCode = hcpRefundCode;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Integer getRefundTimes() {
		return refundTimes;
	}

	public void setRefundTimes(Integer refundTimes) {
		this.refundTimes = refundTimes;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCpStartDate() {
		return cpStartDate;
	}

	public void setCpStartDate(String cpStartDate) {
		this.cpStartDate = cpStartDate;
	}

	public String getCpEndDate() {
		return cpEndDate;
	}

	public void setCpEndDate(String cpEndDate) {
		this.cpEndDate = cpEndDate;
	}

	public String getSqStartDate() {
		return sqStartDate;
	}

	public void setSqStartDate(String sqStartDate) {
		this.sqStartDate = sqStartDate;
	}

	public String getSqEndDate() {
		return sqEndDate;
	}

	public void setSqEndDate(String sqEndDate) {
		this.sqEndDate = sqEndDate;
	}
	
}
