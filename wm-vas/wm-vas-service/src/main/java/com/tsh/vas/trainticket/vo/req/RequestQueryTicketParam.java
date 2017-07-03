/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.req;

/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
public class RequestQueryTicketParam extends BaseRequestParam{

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


    public String getTravelTime() {
        return travelTime;
    }


    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }


    public String getFromStation() {
        return fromStation;
    }


    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }


    public String getArriveStation() {
        return arriveStation;
    }


    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }
}
