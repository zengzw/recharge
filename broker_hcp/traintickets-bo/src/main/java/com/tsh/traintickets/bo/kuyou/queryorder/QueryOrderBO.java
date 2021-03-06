package com.tsh.traintickets.bo.kuyou.queryorder;

import com.tsh.traintickets.bo.kuyou.BaseBO;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class QueryOrderBO extends BaseBO {
    private String merchant_order_id;   //	商户订单Id
    private String order_id;            //	订单ID
    private TicketInfo ticket_list;     // 订单内车票信息

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

    public TicketInfo getTicket_list() {
        return ticket_list;
    }

    public void setTicket_list(TicketInfo ticket_list) {
        this.ticket_list = ticket_list;
    }
}
