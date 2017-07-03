package com.tsh.traintickets.vo.request;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class RefundTicketCallbackRequest extends BaseSerializable{
    private String request_id;//	合作商户退款流水号
    private String trip_no;//	19旅行退款流水号
    private String merchant_order_id;//	商户订单Id（协议参数）
    private String status;//	处理结果
    private String refund_total_amount;//	退款金额
    private String refund_type;//	退款类型
    private String order_id;//	19旅行订单Id
    private List<RefundOrderUserInfo> order_userinfo;// 订单内乘客信息

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getTrip_no() {
        return trip_no;
    }

    public void setTrip_no(String trip_no) {
        this.trip_no = trip_no;
    }

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public void setMerchant_order_id(String merchant_order_id) {
        this.merchant_order_id = merchant_order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefund_total_amount() {
        return refund_total_amount;
    }

    public void setRefund_total_amount(String refund_total_amount) {
        this.refund_total_amount = refund_total_amount;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<RefundOrderUserInfo> getOrder_userinfo() {
        return order_userinfo;
    }

    public void setOrder_userinfo(List<RefundOrderUserInfo> order_userinfo) {
        this.order_userinfo = order_userinfo;
    }
}
