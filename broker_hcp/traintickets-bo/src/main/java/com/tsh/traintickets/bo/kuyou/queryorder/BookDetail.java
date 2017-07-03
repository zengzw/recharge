package com.tsh.traintickets.bo.kuyou.queryorder;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class BookDetail extends BaseSerializable{
    private String bx;//	是否购买19旅行保险产品
    private String ids_type;//	证件类型
    private String ticket_type;//	车票类型
    private String user_ids;//	证件号码
    private String user_name;//	姓名
    private String bx_code;//	保险单号

    public String getBx() {
        return bx;
    }

    public void setBx(String bx) {
        this.bx = bx;
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

    public String getBx_code() {
        return bx_code;
    }

    public void setBx_code(String bx_code) {
        this.bx_code = bx_code;
    }
}
