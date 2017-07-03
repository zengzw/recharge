package com.tsh.vas.vo.trainticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;



public class HcpOrderInfoVo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**  终点站*/
    private String arriveStation;


    /**  付费用户账户角色类型（2：平台管理，3：县域，4：网点，5：供应商，9：会员）*/
    private Integer bizType;
    
    
    /**  父级服务业务编号*/
    private String businessCode;
    
    
    
    /**  父级服务业务名称*/
    private String businessName;
    
    
    /**  是否购买保险*/
    private Integer bx;
    

    /**  成本价*/
    private BigDecimal costingAmount;


    /**  始发站*/
    private String fromStation;

    /**
     * 用户姓名
     */
    private String userName;

    
    /**
     * 支付密码
     */
    private String payPassword;
    
    
    /**  支付会员用户电话*/
    private String mobile;
    

    /**  应付金额*/
    private BigDecimal originalAmount;
    
    
    

    /**  实付金额*/
    private BigDecimal realAmount;


    /**  途径时长（分钟）*/
    private String costTime;


    /**  座位类型*/
    private Integer seatType;
    
    /**
     * 供应商的服务器地址
     */
    private String serverAddr;
    
    
    /**  服务费用*/
    private  Double servicePrice;
    
    
    /**  火车到达时间*/
    private String stationArriveTime;
    
    
    /**  火车开车时间*/
    private String stationStartTime;
    

    /**  供应商名称*/
    private String supplierName;
    
    
    /**
     * 增值服务与开放平台通讯的供应商key
     */
    private String supplierToken;
    
    /**
     * 供应商编码
     */
    private String supplierCode;
    
    
    
    /**  车票价格*/
    private Double ticketPrice;



    /**  车次*/
    private String trainCode;



    /**  乘车日期*/
    private String travelTime;
    
    
    
    /**
     * 车票类型
     */
    private String ticketType;



    /**
     *  用户成功列表
     */
    private List<TrainUserInfoVo> userDetailList;
    
    
    /**
     * 余票不足部分提交。
     * 0：不支持，1：支持
     */
    private Integer partSubmit;



    public Integer getPartSubmit() {
        return partSubmit;
    }


    public void setPartSubmit(Integer partSubmit) {
        this.partSubmit = partSubmit;
    }


    public String getArriveStation() {
        return arriveStation;
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



    public Integer getBx() {
        return bx;
    }



    public BigDecimal getCostingAmount() {
        return costingAmount;
    }



    public String getFromStation() {
        return fromStation;
    }



    public String getMobile() {
        return mobile;
    }



    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }



    public String getPayPassword() {
        return payPassword;
    }



    public BigDecimal getRealAmount() {
        return realAmount;
    }



    public Integer getSeatType() {
        return seatType;
    }



    public String getServerAddr() {
        return serverAddr;
    }



    public Double getServicePrice() {
        return servicePrice;
    }



    public String getStationArriveTime() {
        return stationArriveTime;
    }


    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getStationStartTime() {
        return stationStartTime;
    }



    public String getSupplierCode() {
        return supplierCode;
    }



    public String getSupplierName() {
        return supplierName;
    }



    public String getSupplierToken() {
        return supplierToken;
    }



    public Double getTicketPrice() {
        return ticketPrice;
    }



    public String getTicketType() {
        return ticketType;
    }



    public String getTrainCode() {
        return trainCode;
    }



    public String getTravelTime() {
        return travelTime;
    }



    public List<TrainUserInfoVo> getUserDetailList() {
        return userDetailList;
    }



    public String getUserName() {
        return userName;
    }



    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }



    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }



    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }



    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }



    public void setBx(Integer bx) {
        this.bx = bx;
    }



    public void setCostingAmount(BigDecimal costingAmount) {
        this.costingAmount = costingAmount;
    }



    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }



    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }



    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }



    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }



    public void setSeatType(Integer seatType) {
        this.seatType = seatType;
    }



    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }



    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }



    public void setStationArriveTime(String stationArriveTime) {
        this.stationArriveTime = stationArriveTime;
    }



    public void setStationStartTime(String stationStartTime) {
        this.stationStartTime = stationStartTime;
    }



    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }



    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }



    public void setSupplierToken(String supplierToken) {
        this.supplierToken = supplierToken;
    }



    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }



    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }



    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }



    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }



    public void setUserDetailList(List<TrainUserInfoVo> userDetailList) {
        this.userDetailList = userDetailList;
    }
    
    
    
    public void setUserName(String userName) {
        this.userName = userName;
    }



	@Override
	public String toString() {
		return "HcpOrderInfoVo [arriveStation=" + arriveStation + ", bizType="
				+ bizType + ", businessCode=" + businessCode
				+ ", businessName=" + businessName + ", bx=" + bx
				+ ", costingAmount=" + costingAmount + ", fromStation="
				+ fromStation + ", userName=" + userName + ", payPassword="
				+ payPassword + ", mobile=" + mobile + ", originalAmount="
				+ originalAmount + ", realAmount=" + realAmount + ", seatType="
				+ seatType + ", serverAddr=" + serverAddr + ", servicePrice="
				+ servicePrice + ", stationArriveTime=" + stationArriveTime
				+ ", stationStartTime=" + stationStartTime + ", supplierName="
				+ supplierName + ", supplierToken=" + supplierToken
				+ ", supplierCode=" + supplierCode + ", ticketPrice="
				+ ticketPrice + ", trainCode=" + trainCode + ", travelTime="
				+ travelTime + ", ticketType=" + ticketType
				+ ", userDetailList=" + userDetailList + "]";
	}
}
