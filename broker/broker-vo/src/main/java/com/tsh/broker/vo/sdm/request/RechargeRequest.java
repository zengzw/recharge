package com.tsh.broker.vo.sdm.request;

import com.tsh.broker.vo.common.BaseRequest;

import java.math.BigDecimal;

/**
 * RechargeQuery
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class RechargeRequest extends BaseRequest {

    private String inOrderNo;	//内部订单编码	订单编号
    private String outOrderNo;	//外部订单编号
    private String unitId;	//缴费单位ID
    private String account;	//缴费账户
    private BigDecimal payAmount;	//缴费金额
    private String yearmonth;	//账单月份

    private  String     productId;   //产品ID
    private  Integer   payType;    	    //缴费类型	1.水费2.电费3.煤气
    private  Integer   accountType;     //账户类型  1.户号 2.条形码
    private  String    province;    	//省份/直辖市
    private  String    city;    		//城市/区
    private  String    county;    		//县/区
    private String  extContent;         //扩展字段


    public String getInOrderNo() {
        return inOrderNo;
    }

    public void setInOrderNo(String inOrderNo) {
        this.inOrderNo = inOrderNo;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }

}
