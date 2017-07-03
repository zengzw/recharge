/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo.phone.api;

/**
 *  下单成功之后返回对象
 *  
 * @author zengzw
 * @date 2017年3月13日
 */
public class APISuccessOrderInfo {
    
    private String orderCode;// 订单编号

    private String mobile;// 支付手机号
    
    private String originalAmount; //应付金额
    
    private String realAmount;  //实付金额
    
    private  String rechargeValue; //充值面额
    
    private String rechargeMobile; //充值手机号码


    private String createTime;// 订单创建时间


    private Integer payStatus;// 0:失败，1：成功
    
    
    private String weixPayUrl; //执行支付Url


    public String getWeixPayUrl() {
        return weixPayUrl;
    }


    public void setWeixPayUrl(String weixPayUrl) {
        this.weixPayUrl = weixPayUrl;
    }


    public String getCreateTime() {
        return createTime;
    }


    public String getMobile() {
        return mobile;
    }


    public String getOrderCode() {
        return orderCode;
    }


    public String getOriginalAmount() {
        return originalAmount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }
    
    public String getRealAmount() {
        return realAmount;
    }
    
   
    
    public String getRechargeMobile() {
        return rechargeMobile;
    }


    public String getRechargeValue() {
        return rechargeValue;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


  
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

   

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


  
    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public void setRechargeMobile(String rechargeMobile) {
        this.rechargeMobile = rechargeMobile;
    }


    public void setRechargeValue(String rechargeValue) {
        this.rechargeValue = rechargeValue;
    }
}
