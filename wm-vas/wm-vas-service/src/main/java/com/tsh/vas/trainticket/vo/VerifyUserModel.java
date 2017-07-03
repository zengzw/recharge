package com.tsh.vas.trainticket.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class VerifyUserModel implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 5549724620921197064L;

    /**
     * 证件类型
     * 
     * "1、一代身份证、２、二代身份证、３、港澳通行证、４、台湾通行证、５、护照"
     */
    private String idType;

    /**
     * 证件号
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
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

}
