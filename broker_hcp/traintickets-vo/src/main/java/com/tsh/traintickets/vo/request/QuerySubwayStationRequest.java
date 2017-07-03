package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class QuerySubwayStationRequest extends BaseRequest {

    private String trainCode;

    private String arriveStation;

    private String fromStation;


    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }
}
