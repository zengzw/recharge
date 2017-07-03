package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class RefundTicketRequest extends BaseRequest{

    private String comment;//	用户退票说明
    private String merchantOrderId;//	商户订单Id（协议参数）
    private String orderId;//	订单ID（协议参数）
    private String refundResultUrl;//	退票订单处理结果通知URL（协议参数）
    private String refundType;//	订单退票类型
    private String requestId;//	退款流水号
//    private List<UserModel> userList;//  退票乘客

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getRefundResultUrl() {
        return refundResultUrl;
    }

    public void setRefundResultUrl(String refundResultUrl) {
        this.refundResultUrl = refundResultUrl;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

//    public List<UserModel> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(List<UserModel> userList) {
//        this.userList = userList;
//    }
}
