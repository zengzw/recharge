package com.tsh.vas.vo.trainticket.screen;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/5 005.
 */
public class HcpOrderDetail {

    private String chargeCode;//	火车票订单编号
    private String payStatus;//	支付状态
    private String payStatusName;//	支付状态名称
    private String refundStatus;//	退款状态
    private String refundStatusName;//	退款状态名称
    private String rechargeUserCode;//	用户姓名和手机号
    private String createTime;//	创建时间/支付时间
    private String createTimeStr;
    private String trainCode;//	车次
    private String realAmount;//	订单金额
    private String seatType;//	座位类型
    private String seatTypeName;//  座位类型名称
    private String fromStation;//	出发站
    private String arriveStation;//	终点站
    private String stationStartTime;//	火车开车时间
    private String stationArriveTime;//	火车到达时间
    private String originalAmount;//	应付金额
    private String costingAmount;//	成本价
    private String servicePrice;//	服务费用
    private String mobile;//	支付会员用户电话
    private String payUserName;//	支付会员用户姓名
    private String payWay;//	支付方式
    private String payWayName;  //  支付方式名称
    private String userName;//	乘客姓名
    private String merchantOrderId;// 外部订单编号
    private String orderId;// 供应商订单编号
    private String canRefund;//是否可以退款
    private String canRefundAmount;// 可退金额
    private String refundTicketStatus;//退票状态
    private String refundTicketStatusName;//退票状态描述
    private String isRefundTicket;//是否退票成功（1：成功，0：失败）
    private String costTime; // 途径时长

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatusName() {
        return refundStatusName;
    }

    public void setRefundStatusName(String refundStatusName) {
        this.refundStatusName = refundStatusName;
    }

    public String getRechargeUserCode() {
        return rechargeUserCode;
    }

    public void setRechargeUserCode(String rechargeUserCode) {
        this.rechargeUserCode = rechargeUserCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
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

    public String getStationStartTime() {
        return stationStartTime;
    }

    public void setStationStartTime(String stationStartTime) {
        this.stationStartTime = stationStartTime;
    }

    public String getStationArriveTime() {
        return stationArriveTime;
    }

    public void setStationArriveTime(String stationArriveTime) {
        this.stationArriveTime = stationArriveTime;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getCostingAmount() {
        return costingAmount;
    }

    public void setCostingAmount(String costingAmount) {
        this.costingAmount = costingAmount;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
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

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCanRefund() {
        return canRefund;
    }

    public void setCanRefund(String canRefund) {
        this.canRefund = canRefund;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getCanRefundAmount() {
        return canRefundAmount;
    }

    public void setCanRefundAmount(String canRefundAmount) {
        this.canRefundAmount = canRefundAmount;
    }

    public String getRefundTicketStatus() {
        return refundTicketStatus;
    }

    public void setRefundTicketStatus(String refundTicketStatus) {
        this.refundTicketStatus = refundTicketStatus;
    }

    public String getRefundTicketStatusName() {
        return refundTicketStatusName;
    }

    public void setRefundTicketStatusName(String refundTicketStatusName) {
        this.refundTicketStatusName = refundTicketStatusName;
    }

    public String getIsRefundTicket() {
        return isRefundTicket;
    }

    public void setIsRefundTicket(String isRefundTicket) {
        this.isRefundTicket = isRefundTicket;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
