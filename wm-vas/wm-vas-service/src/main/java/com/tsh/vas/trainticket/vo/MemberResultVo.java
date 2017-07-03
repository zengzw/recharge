/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo;


/**
 *
 * 会员信息
 * 
 * @author zengzw
 * @date 2016年12月5日
 */
public class MemberResultVo {
    // 所属账户的商业ID.支付账户
    private Integer bizId;

    //支付用户ID
    private Integer userId;

    // 所属账户的商业类型.支付账户，网点，会员，找邓松
    private Integer bizType;

    //支付用户名
    private String userName;
    
    //会员名称
    private String memberName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * 手机号码
     */
    private String mobiel;

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobiel() {
        return mobiel;
    }

    public void setMobiel(String mobiel) {
        this.mobiel = mobiel;
    }
}
