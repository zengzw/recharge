package com.tsh.vas.vo.charge;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * RechargeParams
 *
 * @author dengjd
 * @date 2016/10/20
 */
public class RechargeParams<T> {

    /**
     * 供应商编号
     */
    private String supplierCode;

    /**
     * 增值服务与开放平台通讯的供应商key
     */
    private String supplierToken;
    /**
     * 供应商的服务器地址
     */
    private String serverAddr;

    /**
     * 业务数据
     */
    private T data;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }


    public String getSupplierToken() {
        return supplierToken;
    }

    public void setSupplierToken(String supplierToken) {
        this.supplierToken = supplierToken;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}
}
