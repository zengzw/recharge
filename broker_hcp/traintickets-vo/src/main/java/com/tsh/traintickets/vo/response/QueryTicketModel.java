package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class QueryTicketModel extends BaseSerializable{

    private String arriveStation;      //到达站
    private String arriveTime;         //到达时间
    private String costTime;           //历时(分钟)
    private String endStation;         //终点站
    private String endTime;            //终点时间
    private String fromStation;        //出发站
    private String fromTime;           //出发时间
    private String gwNum;              //高级软卧票数
    private String gws;                 //高级软卧上铺票价
    private String gwx;                 //高级软卧下铺票价
    private String rwNum;              //软卧票数
    private String rws;                 //软卧上铺票价
    private String rwx;                 //软卧下铺票价
    private String rz;                  //软座票价
    private String rz1;                 //一等座票价
    private String rz1Num;             //一等座票数
    private String rz2;                 //二等座票价
    private String rz2Num;             //二等座票数
    private String rzNum;              //软座票数
    private String startStation;       //始发站
    private String startTime;          //始发时间
    private String swz;                 //商务座票价
    private String swzNum;             //商务座票数
    private String tdz;                 //特等座票价
    private String tdzNum;             //特等座票数
    private String trainCode;          //车次
    private String trainPro1;          //保留字段1
    private String trainPro2;          //保留字段2
    private String wz;                  //无座票价
    private String wzNum;              //无座票数
    private String ywNum;              //硬卧票数
    private String yws;                 //硬卧上票价
    private String ywx;                 //硬卧下票价
    private String ywz;                 //硬卧中票价
    private String yz;                  //硬座票价
    private String yzNum;              //硬座票数
    private String trainType;           //车次类型

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

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
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

    public String getGws() {
        return gws;
    }

    public void setGws(String gws) {
        this.gws = gws;
    }

    public String getGwx() {
        return gwx;
    }

    public void setGwx(String gwx) {
        this.gwx = gwx;
    }

    public String getRwNum() {
        return rwNum;
    }

    public void setRwNum(String rwNum) {
        this.rwNum = rwNum;
    }

    public String getRws() {
        return rws;
    }

    public void setRws(String rws) {
        this.rws = rws;
    }

    public String getRwx() {
        return rwx;
    }

    public void setRwx(String rwx) {
        this.rwx = rwx;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public String getRz1() {
        return rz1;
    }

    public void setRz1(String rz1) {
        this.rz1 = rz1;
    }

    public String getRz1Num() {
        return rz1Num;
    }

    public void setRz1Num(String rz1Num) {
        this.rz1Num = rz1Num;
    }

    public String getRz2() {
        return rz2;
    }

    public void setRz2(String rz2) {
        this.rz2 = rz2;
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

    public String getSwz() {
        return swz;
    }

    public void setSwz(String swz) {
        this.swz = swz;
    }

    public String getSwzNum() {
        return swzNum;
    }

    public void setSwzNum(String swzNum) {
        this.swzNum = swzNum;
    }

    public String getTdz() {
        return tdz;
    }

    public void setTdz(String tdz) {
        this.tdz = tdz;
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

    public String getTrainPro1() {
        return trainPro1;
    }

    public void setTrainPro1(String trainPro1) {
        this.trainPro1 = trainPro1;
    }

    public String getTrainPro2() {
        return trainPro2;
    }

    public void setTrainPro2(String trainPro2) {
        this.trainPro2 = trainPro2;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
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

    public String getYws() {
        return yws;
    }

    public void setYws(String yws) {
        this.yws = yws;
    }

    public String getYwx() {
        return ywx;
    }

    public void setYwx(String ywx) {
        this.ywx = ywx;
    }

    public String getYwz() {
        return ywz;
    }

    public void setYwz(String ywz) {
        this.ywz = ywz;
    }

    public String getYz() {
        return yz;
    }

    public void setYz(String yz) {
        this.yz = yz;
    }

    public String getYzNum() {
        return yzNum;
    }

    public void setYzNum(String yzNum) {
        this.yzNum = yzNum;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }
}
