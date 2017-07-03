package com.tsh.traintickets.bo.kuyou.ordercallback;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/23 023.
 */
public class OrderCallBackBO extends BaseSerializable{

    private String merchantOrderId;//	商户订单Id
    private String tradeNo;//	支付流水号
    private String status;//	处理结果
    private String failReason;//	失败原因
    private String amount;//	支付金额
    private String refundAmount;//	退款金额
    private String refundType;//	退款类型
    private String orderId;//	19旅行订单Id
    private String outTicketBillno;//	12306订单Id
    private String outTicketTime;//	出票成功时间
    private String bxPayMoney;//	保险金额

    private String idType;//	证件类型
    private String ticketType;//	车票类型
    private String userId;//	证件号码
    private String userName;//	姓名
    private String trainBox;//	车厢号
    private String seatNo;//	座位号
    private String seatType;//	座位类型

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOutTicketBillno() {
        return outTicketBillno;
    }

    public void setOutTicketBillno(String outTicketBillno) {
        this.outTicketBillno = outTicketBillno;
    }

    public String getOutTicketTime() {
        return outTicketTime;
    }

    public void setOutTicketTime(String outTicketTime) {
        this.outTicketTime = outTicketTime;
    }

    public String getBxPayMoney() {
        return bxPayMoney;
    }

    public void setBxPayMoney(String bxPayMoney) {
        this.bxPayMoney = bxPayMoney;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrainBox() {
        return trainBox;
    }

    public void setTrainBox(String trainBox) {
        this.trainBox = trainBox;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
