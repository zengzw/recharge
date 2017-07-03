package com.tsh.vas.vo.trainticket;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单报表Po
 * 
 * @version 1.0.0 2016年11月29日<br>
 * @see
 * @since JDK 1.7.0
 */
public class HcpOrderInfoReportsReturnVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String hcpOrderCode; //订单编号
	private String openPlatformNo; //供应商订单编号
	private String supplierName;	//服务供应商
	private String userName;	//乘车人
	private String idCardId;	//乘车人身份证
	private Integer payStatus;	//订单状态
	private Date createTime;	 //支付时间
	private String createTimeStr; //支付时间str
	private String cpDateTimeStr; //出票时间
	private String sqDateTimeStr; //申请退票时间
	private String linkName;	//购票联系人姓名
	private String linkPhone;	//购票联系人电话
	private String storeName;	//购票网点
	private Float originalAmount;	//支付金额
	private Float costingAmount;	//票价
	private Float platformDividedRatio; //平台和县域，平台分成比
	private Float areaDividedRatio; //平台和县域，县域分成比
	private Float areaCommissionRatio;	//县域和网点，县域佣金比
	private Float storeCommissionRatio;	//县域和网点，网点佣金比
	
	private String platformDividedRatioStr; //平台分成金额(元)
	private String areaCommissionRatioStr;	//县域佣金(元)
	private String storeCommissionRatioStr; //网点佣金(元)
	
	private Integer status; //退款状态
	private Float realAmount;	//退款金额（元）
	private String hcpRefundCode; //退款单号
	private String refundAmountCode; //退款流水编号
	
	private String tdAmount; //提点金额
	
	private String trainCode; //车次
	private String stationStartTime; //开车时间
	private String fromStation; //出发地
	private String arriveStation; //目的地
	private Integer seatType; //坐席(0、商务座  1、特等座  2、一等座  3、二等座  4、高级软卧  5、软卧  6、硬卧  7、软座  8、硬座  9、无座  10、其他)
	private Integer ticketType; //票种 (0-成人票)
	private String seatTypeStr; //坐席
	private String ticketTypeStr; //票种
	private String province;
	private String city;
	private String countryName;
	private String payStatusStr; //订单状态
	private String statusStr; //退款状态
	private Integer refundStatus; //退票状态(1：待退票，2：退票中，5：退票失败，13：退票成功/待退款，14：关闭)
	private String refundStatusStr; //退票状态
	private String supplierOrderId; //第三方返回的订单号
	
	public String getSupplierOrderId() {
		return supplierOrderId;
	}
	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}
	public String getRefundAmountCode() {
		return refundAmountCode;
	}
	public void setRefundAmountCode(String refundAmountCode) {
		this.refundAmountCode = refundAmountCode;
	}
	public String getSeatTypeStr() {
		return seatTypeStr;
	}
	public void setSeatTypeStr(String seatTypeStr) {
		this.seatTypeStr = seatTypeStr;
	}
	public String getTicketTypeStr() {
		return ticketTypeStr;
	}
	public void setTicketTypeStr(String ticketTypeStr) {
		this.ticketTypeStr = ticketTypeStr;
	}
	public String getPayStatusStr() {
		return payStatusStr;
	}
	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
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
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCpDateTimeStr() {
		return cpDateTimeStr;
	}
	public void setCpDateTimeStr(String cpDateTimeStr) {
		this.cpDateTimeStr = cpDateTimeStr;
	}
	public String getSqDateTimeStr() {
		return sqDateTimeStr;
	}
	public void setSqDateTimeStr(String sqDateTimeStr) {
		this.sqDateTimeStr = sqDateTimeStr;
	}
	public String getTrainCode() {
		return trainCode;
	}
	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
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
	public String getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}
	public Integer getSeatType() {
		return seatType;
	}
	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}
	public Integer getTicketType() {
		return ticketType;
	}
	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getIdCardId() {
		return idCardId;
	}
	public void setIdCardId(String idCardId) {
		this.idCardId = idCardId;
	}
	
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public Float getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(Float originalAmount) {
		this.originalAmount = originalAmount;
	}
	
	public Float getCostingAmount() {
		return costingAmount;
	}
	public void setCostingAmount(Float costingAmount) {
		this.costingAmount = costingAmount;
	}
	
	public Float getAreaCommissionRatio() {
		return areaCommissionRatio;
	}
	public void setAreaCommissionRatio(Float areaCommissionRatio) {
		this.areaCommissionRatio = areaCommissionRatio;
	}
	
	public Float getStoreCommissionRatio() {
		return storeCommissionRatio;
	}
	public void setStoreCommissionRatio(Float storeCommissionRatio) {
		this.storeCommissionRatio = storeCommissionRatio;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Float getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(Float realAmount) {
		this.realAmount = realAmount;
	}
	
	public String getHcpRefundCode() {
		return hcpRefundCode;
	}
	public void setHcpRefundCode(String hcpRefundCode) {
		this.hcpRefundCode = hcpRefundCode;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getAreaCommissionRatioStr() {
		return areaCommissionRatioStr;
	}
	public void setAreaCommissionRatioStr(String areaCommissionRatioStr) {
		this.areaCommissionRatioStr = areaCommissionRatioStr;
	}
	public String getStoreCommissionRatioStr() {
		return storeCommissionRatioStr;
	}
	public void setStoreCommissionRatioStr(String storeCommissionRatioStr) {
		this.storeCommissionRatioStr = storeCommissionRatioStr;
	}
	public Float getPlatformDividedRatio() {
		return platformDividedRatio;
	}
	public void setPlatformDividedRatio(Float platformDividedRatio) {
		this.platformDividedRatio = platformDividedRatio;
	}
	public Float getAreaDividedRatio() {
		return areaDividedRatio;
	}
	public void setAreaDividedRatio(Float areaDividedRatio) {
		this.areaDividedRatio = areaDividedRatio;
	}
	public String getPlatformDividedRatioStr() {
		return platformDividedRatioStr;
	}
	public void setPlatformDividedRatioStr(String platformDividedRatioStr) {
		this.platformDividedRatioStr = platformDividedRatioStr;
	}
	public String getTdAmount() {
		return tdAmount;
	}
	public void setTdAmount(String tdAmount) {
		this.tdAmount = tdAmount;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRefundStatusStr() {
		return refundStatusStr;
	}
	public void setRefundStatusStr(String refundStatusStr) {
		this.refundStatusStr = refundStatusStr;
	}
	
}
