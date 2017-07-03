/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

/** 
 *  退票回调请求参数
 *  
 *
 * @author zengzw
 * @date 2016年12月3日
 */
public class RequestReturnTicketBackParam {

    private String requestId;
    
    private String merchantOrderId;//   商户订单Id（协议参数）
    
    private String orderId;//   订单ID
    
    private String tripNo;//    19旅行退款流水号
    
    private String status;//    处理结果
    
    private String failReason;//    失败原因

    /**
     * 退款金额
     */
    private String refundTotalAmount;
    
    
    /**
     * 1：用户线上退款
        2：用户车站退款（包括车站改签退款）
     */
    private String refundType;  
    
    
    /**
     * 1、一代身份证、２、二代身份证、
                        ３、港澳通行证、４、台湾通行证、５、护照
     */
    private String idType;


    public String getFailReason() {
        return failReason;
    }


    public String getIdType() {
        return idType;
    }


    public String getMerchantOrderId() {
        return merchantOrderId;
    }


    public String getOrderId() {
        return orderId;
    }


    public String getRefundTotalAmount() {
        return refundTotalAmount;
    }


    public String getRefundType() {
        return refundType;
    }


    public String getRequestId() {
        return requestId;
    }


    public String getStatus() {
        return status;
    }


    public String getTripNo() {
        return tripNo;
    }


    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }


    public void setIdType(String idType) {
        this.idType = idType;
    }


    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public void setRefundTotalAmount(String refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
    }


    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }


    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }
}
