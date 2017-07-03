package com.tsh.traintickets.vo.request;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class DealOrderCallBackRequest extends BaseSerializable{

    private String merchant_order_id;//	商户订单Id
    private String trade_no;//	支付流水号
    private String status;//	处理结果
    private String fail_reason;//	失败原因
    private String amount;//	支付金额
    private String refund_amount;//	退款金额
    private String refund_type;//	退款类型
    private String order_id;//	19旅行订单Id
    private String out_ticket_billno;//	12306订单Id
    private String out_ticket_time;//	出票成功时间
    private String bx_pay_money;//	保险金额
    private List<OrderUserInfoModel> order_userinfo;

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public void setMerchant_order_id(String merchant_order_id) {
        this.merchant_order_id = merchant_order_id;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
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

    public String getOut_ticket_billno() {
        return out_ticket_billno;
    }

    public void setOut_ticket_billno(String out_ticket_billno) {
        this.out_ticket_billno = out_ticket_billno;
    }

    public String getOut_ticket_time() {
        return out_ticket_time;
    }

    public void setOut_ticket_time(String out_ticket_time) {
        this.out_ticket_time = out_ticket_time;
    }

    public String getBx_pay_money() {
        return bx_pay_money;
    }

    public void setBx_pay_money(String bx_pay_money) {
        this.bx_pay_money = bx_pay_money;
    }

    public List<OrderUserInfoModel> getOrder_userinfo() {
        return order_userinfo;
    }

    public void setOrder_userinfo(List<OrderUserInfoModel> order_userinfo) {
        this.order_userinfo = order_userinfo;
    }
}
