/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

import java.util.List;

import com.tsh.vas.trainticket.vo.VerifyUserModel;

/**
 * 用户校验
 * 
 * @author zengzw
 * @date 2016年12月8日
 */
public class RequestValidateUserInfoParam extends BaseRequestParam{
    
    private String trainCode; //乘车车次
    
    private String trainTime; //乘车时间
    
    

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    private List<VerifyUserModel> userModels;

    public List<VerifyUserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<VerifyUserModel> userModels) {
        this.userModels = userModels;
    }
}
