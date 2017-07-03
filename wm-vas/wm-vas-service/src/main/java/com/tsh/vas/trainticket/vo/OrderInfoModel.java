package com.tsh.vas.trainticket.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class OrderInfoModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8498432022618722772L;

    private String arriveStation;//	到达车站
    private String arriveTime;//	到达时间
    private String fromStation;//	出发车站
    private String fromTime;//	车发时间

    /**
     *      BOOKING:已支付
            BOOK_SUCCESS:正在出票
            OUT_SUCCESS:出票成功
            OUT_FAIL:出票失败
     */
    private String orderStatus;//	订单状态
    
    private String outTicketTime;//	出票时间
    
    private String payMoney;//	支付金额
    
    private String payTime;//	支付时间
    
    /**
     * NO_RFUND:无退票
       REFUNDING:退票中
       PART_REFUND:部分退票完成
       REFUND_FINISH:退款完成
       REFUSE_REFUND:拒绝退款
     */
    private String refundStatus;//	退票状态
    private String seatType;//	席位类型
    private String ticketPrice;//	车票总额
    private String trainCode;//	车次
    private String travelTime;//	发车日期
    private List<OrderUserInfoModel> users;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOutTicketTime() {
        return outTicketTime;
    }

    public void setOutTicketTime(String outTicketTime) {
        this.outTicketTime = outTicketTime;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public List<OrderUserInfoModel> getUsers() {
        return users;
    }

    public void setUsers(List<OrderUserInfoModel> users) {
        this.users = users;
    }
}
