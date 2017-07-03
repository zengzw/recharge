/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo.trainticket;

import java.io.Serializable;

/**
 *
 * @author zengzw
 * @date 2016年11月28日
 */
public class TrainUserInfoVo implements Serializable {

 /**
     * 
     */
    private static final long serialVersionUID = 2879102094661978370L;

    /*   
  * 1、一代身份证、
            ２、二代身份证、 
            ３、港澳通行证、
            ４、台湾通行证、
            ５、护照
   */
    private Integer idType;//    证件类型

    private String userId;//    证件号码

    private String userName;//  姓名
    
    private String bx;//是否购买19旅行保险产品 0：否1：是
    
    public String getBx() {
        return bx;
    }

    public void setBx(String bx) {
        this.bx = bx;
    }

    private String ticketType;//车票类型 0：成人票  1：儿童票

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
