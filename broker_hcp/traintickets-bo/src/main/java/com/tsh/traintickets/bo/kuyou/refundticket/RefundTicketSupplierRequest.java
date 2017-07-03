package com.tsh.traintickets.bo.kuyou.refundticket;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class RefundTicketSupplierRequest extends BaseSerializable {

    private String comment;//	用户退票说明
    private String merchant_order_id;//	商户订单Id（协议参数）
    private String order_id;//	订单ID（协议参数）
    private String refund_picture_url;//	退款小票图片地址
    private String refund_result_url;//	退票订单处理结果通知URL（协议参数）
    private String refund_type;//	订单退票类型
    private String request_id;//	退款流水号
    private List<RefundUserInfo> refundinfo;// 退票乘客信息

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getRefund_picture_url() {
        return refund_picture_url;
    }

    public void setRefund_picture_url(String refund_picture_url) {
        this.refund_picture_url = refund_picture_url;
    }

    public String getRefund_result_url() {
        return refund_result_url;
    }

    public void setRefund_result_url(String refund_result_url) {
        this.refund_result_url = refund_result_url;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<RefundUserInfo> getRefundinfo() {
        return refundinfo;
    }

    public void setRefundinfo(List<RefundUserInfo> refundinfo) {
        this.refundinfo = refundinfo;
    }
}
