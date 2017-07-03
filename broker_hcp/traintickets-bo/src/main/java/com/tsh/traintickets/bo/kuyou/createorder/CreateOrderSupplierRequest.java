package com.tsh.traintickets.bo.kuyou.createorder;

import com.traintickets.common.BaseSerializable;
import com.tsh.traintickets.bo.kuyou.queryorder.BookDetail;


import java.util.List;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class CreateOrderSupplierRequest extends BaseSerializable {

    private String merchant_order_id;//	商户订单Id
    private String order_level;//	订单级别
    private String order_result_url;//	订单处理结果通知URL
//    private String book_result_url;//	订单预订结果通知URL
    private String arrive_station;//	到达车站
    private String arrive_time;//	到达时间
    private List<BookDetail> book_detail_list;// 订单内乘客信息
    private String bx_invoice;//	是否需要保险发票	必选
    private String bx_invoice_address;//	保险发票联系地址	必选
    private String bx_invoice_phone;//	保险发票联系电话	必选
    private String bx_invoice_receiver;//	保险发票收件人	必选
    private String bx_invoice_zipcode;//	保险发票邮政编号	必选
    private String from_station;//	出发车站	必选
    private String from_time;//	出发时间	必选
    private String link_address;//	联系人地址	必选
    private String link_mail;//	联系人邮箱	必选
    private String link_name;//	联系人姓名	必选
    private String link_phone;//	联系人手机	必选
    private String order_pro1;//	保险格式	必选
    private String order_pro2;//	保留字段2	必选
    private String seat_type;//	座位类型	必选
    private String sms_notify;//	预订车票完成并付款成功是否短信通知用户	必选
    private String sum_amount;//	总计金额	必选
    private String ticket_price;//	车票单价	必选
    private String train_code;//	车次	必选
    private String travel_time;//	乘车日期	必选
    private String wz_ext;//	备选无座	必选
    private String account_name;//	12306账号用户名	非必选
    private String account_pwd;//	12306账号密码	非必选

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public void setMerchant_order_id(String merchant_order_id) {
        this.merchant_order_id = merchant_order_id;
    }

    public String getOrder_level() {
        return order_level;
    }

    public void setOrder_level(String order_level) {
        this.order_level = order_level;
    }

    public String getOrder_result_url() {
        return order_result_url;
    }

    public void setOrder_result_url(String order_result_url) {
        this.order_result_url = order_result_url;
    }

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

    public String getBx_invoice() {
        return bx_invoice;
    }

    public void setBx_invoice(String bx_invoice) {
        this.bx_invoice = bx_invoice;
    }

    public String getBx_invoice_address() {
        return bx_invoice_address;
    }

    public void setBx_invoice_address(String bx_invoice_address) {
        this.bx_invoice_address = bx_invoice_address;
    }

    public String getBx_invoice_phone() {
        return bx_invoice_phone;
    }

    public void setBx_invoice_phone(String bx_invoice_phone) {
        this.bx_invoice_phone = bx_invoice_phone;
    }

    public String getBx_invoice_receiver() {
        return bx_invoice_receiver;
    }

    public void setBx_invoice_receiver(String bx_invoice_receiver) {
        this.bx_invoice_receiver = bx_invoice_receiver;
    }

    public String getBx_invoice_zipcode() {
        return bx_invoice_zipcode;
    }

    public void setBx_invoice_zipcode(String bx_invoice_zipcode) {
        this.bx_invoice_zipcode = bx_invoice_zipcode;
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

    public String getLink_address() {
        return link_address;
    }

    public void setLink_address(String link_address) {
        this.link_address = link_address;
    }

    public String getLink_mail() {
        return link_mail;
    }

    public void setLink_mail(String link_mail) {
        this.link_mail = link_mail;
    }

    public String getLink_name() {
        return link_name;
    }

    public void setLink_name(String link_name) {
        this.link_name = link_name;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getOrder_pro1() {
        return order_pro1;
    }

    public void setOrder_pro1(String order_pro1) {
        this.order_pro1 = order_pro1;
    }

    public String getOrder_pro2() {
        return order_pro2;
    }

    public void setOrder_pro2(String order_pro2) {
        this.order_pro2 = order_pro2;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getSms_notify() {
        return sms_notify;
    }

    public void setSms_notify(String sms_notify) {
        this.sms_notify = sms_notify;
    }

    public String getSum_amount() {
        return sum_amount;
    }

    public void setSum_amount(String sum_amount) {
        this.sum_amount = sum_amount;
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

    public String getWz_ext() {
        return wz_ext;
    }

    public void setWz_ext(String wz_ext) {
        this.wz_ext = wz_ext;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_pwd() {
        return account_pwd;
    }

    public void setAccount_pwd(String account_pwd) {
        this.account_pwd = account_pwd;
    }
}
