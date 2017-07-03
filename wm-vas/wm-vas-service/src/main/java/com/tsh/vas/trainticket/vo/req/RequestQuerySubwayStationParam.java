package com.tsh.vas.trainticket.vo.req;


/**
 * Created by Administrator on 2016/12/3 003.
 */
public class RequestQuerySubwayStationParam extends BaseRequestParam{

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
