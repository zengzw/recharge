/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.vo.recharge;

import java.io.Serializable;

/**
 *  获取手机号码归属地VO
 *
 * @author zengzw
 * @date 2017年2月22日
 */
public class RequestQueryPhoneTypeVo extends BaseRequestVo implements Serializable{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 3673501147045509901L;
    
    
    /**
     * 手机号码
     */
    private String mobileNum;

    /**
     * 充值金额
     */
    private String money;


    public String getMobileNum() {
        return mobileNum;
    }



    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
