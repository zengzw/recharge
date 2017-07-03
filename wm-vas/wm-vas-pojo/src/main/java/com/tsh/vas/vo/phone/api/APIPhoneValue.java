/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo.phone.api;

/**
 *  面值VO
 *  
 * @author zengzw
 * @date 2017年3月7日
 */
public class APIPhoneValue {

    /**  订单支付ID*/
    private Long id;
    
    /**  面额*/
    private Integer value;
    
    /**
     * 商品ID
     */
    private Long goodId;
    
    
    /**
     * 销售价
     */
    private double sellPrice;
    
    
    /**
     * 自编号
     */
    private String subBussinessCode;
    
    

    public String getSubBussinessCode() {
        return subBussinessCode;
    }

    public void setSubBussinessCode(String subBussinessCode) {
        this.subBussinessCode = subBussinessCode;
    }

    public Long getGoodId() {
        return goodId;
    }

    public Long getId() {
        return id;
    }
    

    public double getSellPrice() {
        return sellPrice;
    }

    public Integer getValue() {
        return value;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
