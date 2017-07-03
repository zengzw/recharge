package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class QuerySubwayStationModel extends BaseSerializable{

    private String arrtime;//	到站时间
    private String costtime;//	到站天数
    private String distance;//;//	里程数
    private String interval;//	停车时间
    private String name;//	站名
    private String starttime;//	发车时间
    private String stationno;//	途经站序号
    private String isMiddle;//是否在出发站he到达站之间

    public String getArrtime() {
        return arrtime;
    }

    public void setArrtime(String arrtime) {
        this.arrtime = arrtime;
    }

    public String getCosttime() {
        return costtime;
    }

    public void setCosttime(String costtime) {
        this.costtime = costtime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStationno() {
        return stationno;
    }

    public void setStationno(String stationno) {
        this.stationno = stationno;
    }

    public String getIsMiddle() {
        return isMiddle;
    }

    public void setIsMiddle(String isMiddle) {
        this.isMiddle = isMiddle;
    }
}
