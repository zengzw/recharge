package com.tsh.traintickets.bo.kuyou.checkticket;


import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/18 018.
 */
public class CheckTicketNumSupplierRequest extends BaseSerializable{

    private String train_code;          //车次
    private String travel_time;         //乘车日期
    private String from_station;        //始发站
    private String arrive_station;      //终点站

    public String getTrain_code() {
        return train_code;
    }

    public void setTrain_code(String train_code) {
        this.train_code = train_code;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getFrom_station() {
        return from_station;
    }

    public void setFrom_station(String from_station) {
        this.from_station = from_station;
    }

    public String getArrive_station() {
        return arrive_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }
}
