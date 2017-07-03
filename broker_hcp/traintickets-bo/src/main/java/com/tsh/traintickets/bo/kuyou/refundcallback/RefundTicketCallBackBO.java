package com.tsh.traintickets.bo.kuyou.refundcallback;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/23 023.
 */
public class RefundTicketCallBackBO extends BaseSerializable {

    private String requestId;//	合作商户退款流水号
    private String tripNo;//	19旅行退款流水号
    private String merchantOrderId;//	商户订单Id（协议参数）
    private String status;//	处理结果
    private String refundTotalAmount;//	退款金额
    private String refundType;//	退款类型
    private String orderId;//	19旅行订单Id

    private String idType;//	证件类型
    private String ticketType;//	车票类型
    private String userId;//	证件号码
    private String userName;//	姓名
    private String failReason;//	失败原因

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTripNo() {
        return tripNo;
    }

    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundTotalAmount() {
        return refundTotalAmount;
    }

    public void setRefundTotalAmount(String refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
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

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
