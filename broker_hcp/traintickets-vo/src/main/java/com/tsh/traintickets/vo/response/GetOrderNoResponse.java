package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/25 025.
 */
public class GetOrderNoResponse extends BaseSerializable {
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
