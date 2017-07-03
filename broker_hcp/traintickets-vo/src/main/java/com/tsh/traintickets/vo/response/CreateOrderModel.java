package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class CreateOrderModel extends BaseSerializable{

    private String arriveStation;//	到达车站
    private String arriveTime;//	到达时间
    private String bxPayMoney;//	保险金额
    private String fromStation;//	出发车站
    private String fromTime;//	出发时间
    private String merchantOrderId;//	商户订单Id（协议参数）
    private String orderId;//	订单ID
    private String payMoney;//	支付金额
    private String seatType;//	座位类型
    private String ticketPayMoney;//	票价总额
    private String trainCode;//	车次
    private String travelTime;//	发车日期

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getBxPayMoney() {
        return bxPayMoney;
    }

    public void setBxPayMoney(String bxPayMoney) {
        this.bxPayMoney = bxPayMoney;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
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

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getTicketPayMoney() {
        return ticketPayMoney;
    }

    public void setTicketPayMoney(String ticketPayMoney) {
        this.ticketPayMoney = ticketPayMoney;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

}
