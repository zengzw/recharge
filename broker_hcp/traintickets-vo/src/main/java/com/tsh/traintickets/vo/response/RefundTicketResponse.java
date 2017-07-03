package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class RefundTicketResponse extends BaseSerializable{

    private RefundTicketModel data;

    public RefundTicketModel getData() {
        return data;
    }

    public void setData(RefundTicketModel data) {
        this.data = data;
    }
}
