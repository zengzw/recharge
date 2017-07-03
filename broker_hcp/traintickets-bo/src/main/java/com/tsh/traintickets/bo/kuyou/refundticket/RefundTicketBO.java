package com.tsh.traintickets.bo.kuyou.refundticket;

import com.tsh.traintickets.bo.kuyou.BaseBO;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class RefundTicketBO extends BaseBO{

    private String merchant_order_id;//	商户订单Id（协议参数）
    private String order_id;//	订单ID
    private String trip_no;//	19旅行退款流水号
    private String status;//	处理结果
    private String fail_reason;//	失败原因

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

    public String getTrip_no() {
        return trip_no;
    }

    public void setTrip_no(String trip_no) {
        this.trip_no = trip_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }
}
