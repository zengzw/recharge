package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

/**
 * Created by Administrator on 2016/11/16 016.
 */
public class CreateOrderRequest extends BaseRequest {

    private String merchantOrderId;//	商户订单Id
    private String orderLevel = "0";//	订单级别
    private String orderResultUrl;//	订单处理结果通知URL
    private String arriveStation;//	到达车站
    private String arriveTime;//	到达时间
    private String userDetailList; // 乘客信息
    private String bxInvoice = "0";//	是否需要保险发票
    private String fromStation;//	出发车站
    private String fromTime;//	出发时间
    private String linkAddress;//	联系人地址
    private String linkMail;//	联系人邮箱
    private String linkName;//	联系人姓名
    private String linkPhone;//	联系人手机
    private String seatType;//	座位类型
    private String smsNotify;//	预订车票完成并付款成功是否短信通知用户
    private String sumAmount;//	总计金额
    private String ticketPrice;//	车票单价
    private String trainCode;//	车次
    private String travelTime;//	乘车日期
    private String wzExt;//	备选无座

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(String orderLevel) {
        this.orderLevel = orderLevel;
    }

    public String getOrderResultUrl() {
        return orderResultUrl;
    }

    public void setOrderResultUrl(String orderResultUrl) {
        this.orderResultUrl = orderResultUrl;
    }

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

    public String getUserDetailList() {
        return userDetailList;
    }

    public void setUserDetailList(String userDetailList) {
        this.userDetailList = userDetailList;
    }

    public String getBxInvoice() {
        return bxInvoice;
    }

    public void setBxInvoice(String bxInvoice) {
        this.bxInvoice = bxInvoice;
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

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getLinkMail() {
        return linkMail;
    }

    public void setLinkMail(String linkMail) {
        this.linkMail = linkMail;
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

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSmsNotify() {
        return smsNotify;
    }

    public void setSmsNotify(String smsNotify) {
        this.smsNotify = smsNotify;
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
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

    public String getWzExt() {
        return wzExt;
    }

    public void setWzExt(String wzExt) {
        this.wzExt = wzExt;
    }
}
