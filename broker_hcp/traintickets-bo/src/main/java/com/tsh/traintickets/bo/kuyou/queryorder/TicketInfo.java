package com.tsh.traintickets.bo.kuyou.queryorder;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class TicketInfo extends BaseSerializable{
    private String arrive_station;//	到达车站
    private String arrive_time;//	到达时间
    private List<BookDetail> book_detail_list; // 订单内乘客信息
    private String from_station;//	出发车站
    private String from_time;//	车发时间
    private String order_status;//	订单状态
    private String out_ticket_time;//	出票时间
    private String pay_money;//	支付金额
    private String pay_time;//	支付时间
    private String refund_status;//	退票状态
    private String seat_type;//	席位类型
    private String sparePro1;//	保留字段1
    private String sparePro2;//	保留字段2
    private String ticket_price;//	车票总额
    private String train_code;//	车次
    private String travel_time;//	发车日期

    public String getArrive_station() {
        return arrive_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public List<BookDetail> getBook_detail_list() {
        return book_detail_list;
    }

    public void setBook_detail_list(List<BookDetail> book_detail_list) {
        this.book_detail_list = book_detail_list;
    }

    public String getFrom_station() {
        return from_station;
    }

    public void setFrom_station(String from_station) {
        this.from_station = from_station;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOut_ticket_time() {
        return out_ticket_time;
    }

    public void setOut_ticket_time(String out_ticket_time) {
        this.out_ticket_time = out_ticket_time;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getSparePro1() {
        return sparePro1;
    }

    public void setSparePro1(String sparePro1) {
        this.sparePro1 = sparePro1;
    }

    public String getSparePro2() {
        return sparePro2;
    }

    public void setSparePro2(String sparePro2) {
        this.sparePro2 = sparePro2;
    }

    public String getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(String ticket_price) {
        this.ticket_price = ticket_price;
    }

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
}
