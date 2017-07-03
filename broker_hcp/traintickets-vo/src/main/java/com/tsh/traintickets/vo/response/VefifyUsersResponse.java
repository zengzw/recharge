package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25 025.
 */
public class VefifyUsersResponse extends BaseSerializable{

    private List<String> failUserNameList;

    private List<String> validateFailList;

    public List<String> getFailUserNameList() {
        return failUserNameList;
    }

    public void setFailUserNameList(List<String> failUserNameList) {
        this.failUserNameList = failUserNameList;
    }

    public List<String> getValidateFailList() {
        return validateFailList;
    }

    public void setValidateFailList(List<String> validateFailList) {
        this.validateFailList = validateFailList;
    }
}
