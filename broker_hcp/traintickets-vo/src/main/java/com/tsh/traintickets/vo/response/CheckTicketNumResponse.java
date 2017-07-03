package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class CheckTicketNumResponse extends BaseSerializable {

    private CheckTicketNumModel data;

    public CheckTicketNumModel getData() {
        return data;
    }

    public void setData(CheckTicketNumModel data) {
        this.data = data;
    }
}
