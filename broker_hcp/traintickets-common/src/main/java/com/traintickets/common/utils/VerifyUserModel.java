package com.traintickets.common.utils;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class VerifyUserModel{

    private String idType;
    private String userId;
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
