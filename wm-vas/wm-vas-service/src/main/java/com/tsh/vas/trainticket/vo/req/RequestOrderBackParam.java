/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;


/**
 * 订单 回调请求实体
 * 
 * @author zengzw
 * @date 2016年11月25日
 */
public class RequestOrderBackParam {
    
    /**
     * 商户订单Id
     */
    private  String merchantOrderId;
    
    /**
     * 支付流水号
     */
    private  String tradeNo;
    
    /**
     * 处理结果
     * SUCCESS/FAILURE
                        出票成功/出票失败
     */
    private String status;
    
    /**
     * 失败原因
     */
    private String failReason;
    
    /**
     * 支付金额
     */
    private String amount;
    
    /**
     * 
     * 退款金额
     */
    private String refundAmount;
    
    /**
     * 退款类型
     */
    private String refundType;
    
    /**
     * 19旅行订单Id
     */
    private String orderId;
    
    /**
     * 12306订单Id
     */
    private String outTicketBillno;
    
    
    /**
     * 出票成功时间
     */
    private String outTicketTime;
    
    
    /**
     * 保险金额
     */
    private String bxPayMoney;
    
    /**
     * 车厢号
     */
    private String trainBox;
    
    /**
     * 座位号
     */
    private String seatNo;
    
    /**
     * 座位类型
     */
    private String seatType;


    public String getSeatType() {
        return seatType;
    }


    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }


    public String getAmount() {
        return amount;
    }


    public String getBxPayMoney() {
        return bxPayMoney;
    }


    public String getFailReason() {
        return failReason;
    }


    public String getMerchantOrderId() {
        return merchantOrderId;
    }


    


    public String getOrderId() {
        return orderId;
    }


    public String getOutTicketBillno() {
        return outTicketBillno;
    }


    public String getOutTicketTime() {
        return outTicketTime;
    }


    public String getRefundAmount() {
        return refundAmount;
    }


    public String getRefundType() {
        return refundType;
    }


    public String getSeatNo() {
        return seatNo;
    }


    public String getStatus() {
        return status;
    }


    public String getTradeNo() {
        return tradeNo;
    }


    public String getTrainBox() {
        return trainBox;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }


    public void setBxPayMoney(String bxPayMoney) {
        this.bxPayMoney = bxPayMoney;
    }


    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }


    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public void setOutTicketBillno(String outTicketBillno) {
        this.outTicketBillno = outTicketBillno;
    }


    public void setOutTicketTime(String outTicketTime) {
        this.outTicketTime = outTicketTime;
    }


    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }


    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }


    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }


    public void setTrainBox(String trainBox) {
        this.trainBox = trainBox;
    }
    

}
