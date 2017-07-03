package com.tsh.traintickets.vo.request;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class OrderUserInfoModel extends BaseSerializable{
    private String amount;//	票价
    private String bx_channel;//	保险渠道
    private String bx_code;//	保险单号
    private String bx_price;//	保险单价
    private String ids_type;//	证件类型
    private String ticket_type;//	车票类型
    private String user_ids;//	证件号码
    private String user_name;//	姓名
    private String train_box;//	车厢号
    private String seat_no;//	座位号
    private String seat_type;//	座位类型

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBx_channel() {
        return bx_channel;
    }

    public void setBx_channel(String bx_channel) {
        this.bx_channel = bx_channel;
    }

    public String getBx_code() {
        return bx_code;
    }

    public void setBx_code(String bx_code) {
        this.bx_code = bx_code;
    }

    public String getBx_price() {
        return bx_price;
    }

    public void setBx_price(String bx_price) {
        this.bx_price = bx_price;
    }

    public String getIds_type() {
        return ids_type;
    }

    public void setIds_type(String ids_type) {
        this.ids_type = ids_type;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTrain_box() {
        return train_box;
    }

    public void setTrain_box(String train_box) {
        this.train_box = train_box;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }
}
