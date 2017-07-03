package com.tsh.vas.phone.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/23 023.
 */
public class QueryCouponListParams implements Serializable{

    private String amount;
    private String mobile;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
