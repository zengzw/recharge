package com.tsh.traintickets.vo.request;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class RefundOrderUserInfo extends BaseSerializable{

    private String ids_type;//	证件类型
    private String ticket_type;//	车票类型
    private String user_ids;//	证件号码
    private String user_name;//	姓名
    private String fail_reason;//	失败原因
    private String status;//	处理结果
    private String refund_amount;//	退款金额

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

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }
}
