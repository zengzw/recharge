package com.tsh.vas.trainticket.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class OrderUserInfoModel implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 4771877177569294234L;
    private String bx;//	是否购买19旅行保险产品
    private String idType;//	证件类型
    private String ticketType;//	车票类型
    private String userId;//	证件号码
    private String userName;//	姓名
    private String bxCode;//	保险单号

    public String getBx() {
        return bx;
    }

    public void setBx(String bx) {
        this.bx = bx;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBxCode() {
        return bxCode;
    }

    public void setBxCode(String bxCode) {
        this.bxCode = bxCode;
    }
}
