package com.tsh.traintickets.bo.kuyou.queryTicket;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class QueryTicketSupplierRequest extends BaseSerializable{

    private String travel_time;
    private String from_station;
    private String arrive_station;

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
