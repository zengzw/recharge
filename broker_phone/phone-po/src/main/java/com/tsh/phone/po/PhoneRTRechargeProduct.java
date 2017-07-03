/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 高阳 手机充值产品表
 * 
 * @author zengzw
 * @date 2016年7月29日
 */


@Entity
@Table(name = "phone_rt_recharge_product")
public class PhoneRTRechargeProduct {
    /**
     * 话费充值产品
     */
    private Integer prodId;

    /**
     * 产品面额
     */
    private Integer prodContent;

    /**
     * 产品支付价格
     */
    private Double prodPrice;

    /**
     * 运营商类别
     */
    private String prodIsptype;


    /**
     * 省份名称 
     */
    private String prodProvinceid;

    /**
     * 产品类型
     */
    private String prodType;
    
    
    /**
     * 状态
     */
    private String status;


    @Column(name="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Id
    @Column(name="prod_Id")
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    @Column(name="prod_content")
    public Integer getProdContent() {
        return prodContent;
    }

    public void setProdContent(Integer prodContent) {
        this.prodContent = prodContent;
    }

    @Column(name="prod_price")
    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    @Column(name="prod_isptype")
    public String getProdIsptype() {
        return prodIsptype;
    }

    public void setProdIsptype(String prodIsptype) {
        this.prodIsptype = prodIsptype;
    }

    @Column(name="prod_provinceid")
    public String getProdProvinceid() {
        return prodProvinceid;
    }

    public void setProdProvinceid(String prodProvinceid) {
        this.prodProvinceid = prodProvinceid;
    }

    @Column(name="prod_type")
    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }


}
