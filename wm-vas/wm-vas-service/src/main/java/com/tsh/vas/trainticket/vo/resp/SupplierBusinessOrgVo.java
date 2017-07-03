/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.resp;

/**
 * 供应商服务 VO
 * 
 * @author zengzw
 * @date 2016年11月25日
 */
public class SupplierBusinessOrgVo {
    /**
     * 供应商的服务器地址
     */
    private String serverAddr;
    
    
    /**
     * 供应商编号
     */
    private String supplierCode;
    
    
    /**
     * 增值服务与开放平台通讯的供应商key
     */
    private String supplierToken;
    
    
    
    public String getServerAddr() {
        return serverAddr;
    }
    public String getSupplierCode() {
        return supplierCode;
    }
    public String getSupplierToken() {
        return supplierToken;
    }
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
    public void setSupplierToken(String supplierToken) {
        this.supplierToken = supplierToken;
    }
}
