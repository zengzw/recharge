package com.tsh.traintickets.bo.kuyou.createorder;

import com.tsh.traintickets.bo.kuyou.BaseBO;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class CreateOrderBO extends BaseBO{
    private String arrive_station;          //	到达车站
    private String arrive_time;             //	到达时间
    private String bx_pay_money;            //	保险金额
    private String from_station;            //	出发车站
    private String from_time;               //	出发时间
    private String merchant_order_id;       //	商户订单Id（协议参数）
    private String order_id;                //	订单ID
    private String pay_money;               //	支付金额
    private String seat_type;               //	座位类型
    private String spare_pro1;              //	保留字段1
    private String spare_pro2;              //	保留字段2
    private String ticket_pay_money;        //	票价总额
    private String train_code;              //	车次
    private String travel_time;             //	发车日期

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

    public String getBx_pay_money() {
        return bx_pay_money;
    }

    public void setBx_pay_money(String bx_pay_money) {
        this.bx_pay_money = bx_pay_money;
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

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public void setMerchant_order_id(String merchant_order_id) {
        this.merchant_order_id = merchant_order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getSpare_pro1() {
        return spare_pro1;
    }

    public void setSpare_pro1(String spare_pro1) {
        this.spare_pro1 = spare_pro1;
    }

    public String getSpare_pro2() {
        return spare_pro2;
    }

    public void setSpare_pro2(String spare_pro2) {
        this.spare_pro2 = spare_pro2;
    }

    public String getTicket_pay_money() {
        return ticket_pay_money;
    }

    public void setTicket_pay_money(String ticket_pay_money) {
        this.ticket_pay_money = ticket_pay_money;
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
