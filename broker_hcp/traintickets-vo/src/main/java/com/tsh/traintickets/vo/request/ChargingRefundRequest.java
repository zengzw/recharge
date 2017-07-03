package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

/**
 * Created by Administrator on 2016/12/2 002.
 */
public class ChargingRefundRequest extends BaseRequest {
    private String trainPrice; // 车票价格
    private String ticketTime; // 开车时间

    public String getTrainPrice() {
        return trainPrice;
    }

    public void setTrainPrice(String trainPrice) {
        this.trainPrice = trainPrice;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }
}
