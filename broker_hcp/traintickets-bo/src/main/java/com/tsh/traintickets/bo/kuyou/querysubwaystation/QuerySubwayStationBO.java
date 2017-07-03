package com.tsh.traintickets.bo.kuyou.querysubwaystation;


import com.tsh.traintickets.bo.kuyou.BaseBO;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class QuerySubwayStationBO extends BaseBO {

    private List<StationInfo> train_stationinfo;

    public List<StationInfo> getTrain_stationinfo() {
        return train_stationinfo;
    }

    public void setTrain_stationinfo(List<StationInfo> train_stationinfo) {
        this.train_stationinfo = train_stationinfo;
    }
}
