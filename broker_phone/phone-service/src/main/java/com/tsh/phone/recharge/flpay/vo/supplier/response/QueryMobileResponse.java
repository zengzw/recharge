package com.tsh.phone.recharge.flpay.vo.supplier.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/12 012.
 */
public class QueryMobileResponse implements Serializable{

    private String City;
    private String MessageCode;
    private String MessageInfo;
    private String Province;
    private String PurchasePrice;
    private String Sp;//联通

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMessageCode() {
        return MessageCode;
    }

    public void setMessageCode(String messageCode) {
        MessageCode = messageCode;
    }

    public String getMessageInfo() {
        return MessageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        MessageInfo = messageInfo;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    public String getSp() {
        return Sp;
    }

    public void setSp(String sp) {
        Sp = sp;
    }
}
