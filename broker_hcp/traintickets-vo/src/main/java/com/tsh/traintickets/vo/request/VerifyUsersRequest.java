package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class VerifyUsersRequest extends BaseRequest {

    private String userList;

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }
}
