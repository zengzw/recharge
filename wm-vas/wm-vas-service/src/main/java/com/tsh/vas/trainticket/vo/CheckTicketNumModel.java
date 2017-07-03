package com.tsh.vas.trainticket.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class CheckTicketNumModel implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8777820002351772822L;
    private String arriveStation;//	到达站
    private String arriveTime;//	到达时间
    private String endStation;//	终点站
    private String endTime;//	终点时间
    private String fromStation;//	出发站
    private String fromTime;//	出发时间
    private String gwNum;//	高级软卧票数
    private String rwNum;//	软卧票数
    private String rz1Num;//	一等座票数
    private String rz2Num;//	二等座票数
    private String rzNum;//	软座票数
    private String startStation;//	始发站
    private String startTime;//	始发时间
    private String swzNum;//	商务座票数
    private String tdzNum;//	特等座票数
    private String trainCode;//	车次
    private String wzNum;//	无座票数
    private String ywNum;//	硬卧票数
    private String yzNum;//	硬座票数

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getGwNum() {
        return gwNum;
    }

    public void setGwNum(String gwNum) {
        this.gwNum = gwNum;
    }

    public String getRwNum() {
        return rwNum;
    }

    public void setRwNum(String rwNum) {
        this.rwNum = rwNum;
    }

    public String getRz1Num() {
        return rz1Num;
    }

    public void setRz1Num(String rz1Num) {
        this.rz1Num = rz1Num;
    }

    public String getRz2Num() {
        return rz2Num;
    }

    public void setRz2Num(String rz2Num) {
        this.rz2Num = rz2Num;
    }

    public String getRzNum() {
        return rzNum;
    }

    public void setRzNum(String rzNum) {
        this.rzNum = rzNum;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSwzNum() {
        return swzNum;
    }

    public void setSwzNum(String swzNum) {
        this.swzNum = swzNum;
    }

    public String getTdzNum() {
        return tdzNum;
    }

    public void setTdzNum(String tdzNum) {
        this.tdzNum = tdzNum;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getWzNum() {
        return wzNum;
    }

    public void setWzNum(String wzNum) {
        this.wzNum = wzNum;
    }

    public String getYwNum() {
        return ywNum;
    }

    public void setYwNum(String ywNum) {
        this.ywNum = ywNum;
    }

    public String getYzNum() {
        return yzNum;
    }

    public void setYzNum(String yzNum) {
        this.yzNum = yzNum;
    }

}
