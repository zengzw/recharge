package com.tsh.vas.model.trainticket;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 订单报表Po
 * 
 * @version 1.0.0 2016年11月29日<br>
 * @see
 * @since JDK 1.7.0
 */
@Entity
public class HcpOrderInfoReports implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String hcpOrderCode; //订单编号
	private String openPlatformNo; //供应商订单编号
	private String supplierName;	//服务供应商
	private String userName;	//乘车人
	private String idCardId;	//乘车人身份证
	private Integer payStatus;	//订单状态
	private Date createTime;	 //支付时间
	/*private String cpDateTime; //出票时间
	private String sqDateTime; //申请退票时间
*/	
	private String linkName;	//购票联系人姓名
	private String linkPhone;	//购票联系人电话
	private String storeName;	//购票网点
	private Float originalAmount;	//支付金额
	private Float costingAmount;	//票价
	
	private Float platformDividedRatio; //平台和县域，平台分成比
	private Float areaDividedRatio; //平台和县域，县域分成比
	private Float areaCommissionRatio;	//县域和网点，县域佣金比
	private Float storeCommissionRatio;	//县域和网点，网点佣金比
	
	private Integer status; //退款状态
	private Float realAmount;	//退款金额（元）
	private String hcpRefundTicketCode; //退款单号
	private String refundAmountCode; //退款流水编号
	
	private Integer refundStatus; //退票状态(1：待退票，2：退票中，5：退票失败，13：退票成功/待退款，14：关闭)
	
	private String supplierOrderId; //第三方返回的订单号
	
	/*private String trainCode; //车次
	private String stationStartTime; //开车时间
	private String fromStation; //出发地
	private String arriveStation; //目的地
	private Integer seatType; //坐席(0、商务座  1、特等座  2、一等座  3、二等座  4、高级软卧  5、软卧  6、硬卧  7、软座  8、硬座  9、无座  10、其他)
	private Integer ticketType; //票种 (0-成人票)
*/	
	
	/*@Column(name="out_ticktet_time")
	public String getCpDateTime() {
		return cpDateTime;
	}
	public void setCpDateTime(String cpDateTime) {
		this.cpDateTime = cpDateTime;
	}
	@Column(name="refund_ticket_create_time")
	public String getSqDateTime() {
		return sqDateTime;
	}
	public void setSqDateTime(String sqDateTime) {
		this.sqDateTime = sqDateTime;
	}*/
	/*@Column(name = "train_code")
	public String getTrainCode() {
		return trainCode;
	}
	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}
	@Column(name = "station_start_time")
	public String getStationStartTime() {
		return stationStartTime;
	}
	public void setStationStartTime(String stationStartTime) {
		this.stationStartTime = stationStartTime;
	}
	@Column(name = "from_station")
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	@Column(name = "arrive_station")
	public String getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}
	@Column(name = "seat_type")
	public Integer getSeatType() {
		return seatType;
	}
	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}
	@Column(name = "ticket_type")
	public Integer getTicketType() {
		return ticketType;
	}
	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}*/
	
	@Column(name = "refund_status")
	public Integer getRefundStatus() {
		return refundStatus;
	}
	@Column(name = "supplier_order_id")
	public String getSupplierOrderId() {
		return supplierOrderId;
	}
	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	@Column(name = "refund_amount_code")
	public String getRefundAmountCode() {
		return refundAmountCode;
	}
	public void setRefundAmountCode(String refundAmountCode) {
		this.refundAmountCode = refundAmountCode;
	}
	
	@Column(name = "platform_divided_ratio")
	public Float getPlatformDividedRatio() {
		return platformDividedRatio;
	}
	public void setPlatformDividedRatio(Float platformDividedRatio) {
		this.platformDividedRatio = platformDividedRatio;
	}
	@Column(name = "area_divided_ratio")
	public Float getAreaDividedRatio() {
		return areaDividedRatio;
	}
	public void setAreaDividedRatio(Float areaDividedRatio) {
		this.areaDividedRatio = areaDividedRatio;
	}
	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "hcp_order_code")
	public String getHcpOrderCode() {
		return hcpOrderCode;
	}
	public void setHcpOrderCode(String hcpOrderCode) {
		this.hcpOrderCode = hcpOrderCode;
	}
	@Column(name = "open_platform_no")
	public String getOpenPlatformNo() {
		return openPlatformNo;
	}
	public void setOpenPlatformNo(String openPlatformNo) {
		this.openPlatformNo = openPlatformNo;
	}
	@Column(name = "supplier_name")
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "id_card_id")
	public String getIdCardId() {
		return idCardId;
	}
	public void setIdCardId(String idCardId) {
		this.idCardId = idCardId;
	}
	@Column(name = "pay_status")
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "link_name")
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	@Column(name = "link_phone")
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	@Column(name = "store_name")
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	@Column(name = "original_amount")
	public Float getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(Float originalAmount) {
		this.originalAmount = originalAmount;
	}
	@Column(name = "costing_amount")
	public Float getCostingAmount() {
		return costingAmount;
	}
	public void setCostingAmount(Float costingAmount) {
		this.costingAmount = costingAmount;
	}
	@Column(name = "area_commission_ratio")
	public Float getAreaCommissionRatio() {
		return areaCommissionRatio;
	}
	public void setAreaCommissionRatio(Float areaCommissionRatio) {
		this.areaCommissionRatio = areaCommissionRatio;
	}
	@Column(name = "store_commission_ratio")
	public Float getStoreCommissionRatio() {
		return storeCommissionRatio;
	}
	public void setStoreCommissionRatio(Float storeCommissionRatio) {
		this.storeCommissionRatio = storeCommissionRatio;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "real_amount")
	public Float getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(Float realAmount) {
		this.realAmount = realAmount;
	}
	@Column(name = "hcp_refund_ticket_code")
	public String getHcpRefundTicketCode() {
		return hcpRefundTicketCode;
	}
	public void setHcpRefundTicketCode(String hcpRefundTicketCode) {
		this.hcpRefundTicketCode = hcpRefundTicketCode;
	}
}
