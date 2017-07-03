/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

/**
 * 查询车次当前所有座位的余票信息 请求
 * @author zengzw
 * @date 2016年11月24日
 */
public class RequestQueryTicketNumParam extends BaseRequestParam{
    /**
     * 乘车时间
     */
    private String travelTime;


    /**
     * 始发站
     */
    private String fromStation;


    /**
     * 终点站
     */
    private String arriveStation;
    
    /**
     * 车次
     */
    private String trainCode;
    
    
    public String getArriveStation() {
        return arriveStation;
    }
    
    public String getFromStation() {
        return fromStation;
    }


    public String getTrainCode() {
        return trainCode;
    }


    public String getTravelTime() {
        return travelTime;
    }


    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }


    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }


    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }


    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }
}
