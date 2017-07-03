package com.tsh.vas.vo.phone;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/23 023.
 */
public class PhoneCouponVo implements Serializable {

    private String name;//优惠券名称

    private String money;//金额

    private String useScope;//使用范围

    private String useBeginTime;//使用开始时间

    private String useEndTime;//使用结束时间

    private Integer recordID;//优惠券ID

    private Integer ruleID;//规则ID

    private String useLinmit;//使用限制


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUseScope() {
        return useScope;
    }

    public void setUseScope(String useScope) {
        this.useScope = useScope;
    }

    public String getUseBeginTime() {
        return useBeginTime;
    }

    public void setUseBeginTime(String useBeginTime) {
        this.useBeginTime = useBeginTime;
    }

    public String getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(String useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public Integer getRuleID() {
        return ruleID;
    }

    public void setRuleID(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public String getUseLinmit() {
        return useLinmit;
    }

    public void setUseLinmit(String useLinmit) {
        this.useLinmit = useLinmit;
    }
}
