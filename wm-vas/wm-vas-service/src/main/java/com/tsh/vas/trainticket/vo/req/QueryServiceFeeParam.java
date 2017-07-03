package com.tsh.vas.trainticket.vo.req;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/13 013.
 */
public class QueryServiceFeeParam implements Serializable{

    private String supplierCode;
    private String businessCode;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
