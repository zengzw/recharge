package com.tsh.broker.vo.sdm.request;

import com.tsh.broker.vo.common.BaseRequest;

/**
 * PaymentBillRequest
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class PaymentBillRequest extends BaseRequest {

    private String unitId;		    //缴费单位ID
    private String unitName;	    //缴费单位ID
    private String account;		    //缴费账户
    private String yearmonth;	    //账单月份
    private String productId;       //产品id

    private  Integer   payType;    	    //缴费类型	1.水费2.电费3.煤气
    private  Integer   accountType;     //账户类型  1.户号 2.条形码
    private  String    province;    	//省份/直辖市
    private  String    city;    		//城市/区
    private  String    county;    		//县/区

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

}
