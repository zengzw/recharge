/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo.recharge;

/**
 * 充值请求VO
 * 
 * @author zengzw
 * @date 2017年2月16日
 */
public class RequestRechargeVo extends BaseRequestVo{
    
    /**
     * 充值手机号码
     */
    private String mobileNum;
    
    
    /**
     * 充值金额
     */
    private int price;
    
    
    /**
     * 省份
     */
    private String provinceName;
    
    
    /**
     * 供应商类型
     */
    private String supplierType;


    public String getMobileNum() {
        return mobileNum;
    }


    public int getPrice() {
        return price;
    }


    public String getProvinceName() {
        return provinceName;
    }


    public String getSupplierType() {
        return supplierType;
    }


    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }


    public void setPrice(int price) {
        this.price = price;
    }


    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

}
