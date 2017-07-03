package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class QuerySubwayStationResponse extends BaseSerializable{

    private List<QuerySubwayStationModel> dataList;

    private String arriveDays;

    public List<QuerySubwayStationModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<QuerySubwayStationModel> dataList) {
        this.dataList = dataList;
    }

    public String getArriveDays() {
        return arriveDays;
    }

    public void setArriveDays(String arriveDays) {
        this.arriveDays = arriveDays;
    }
}
