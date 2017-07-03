package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class CreateOrderResponse extends BaseSerializable {

    private CreateOrderModel data;

    public CreateOrderModel getData() {
        return data;
    }

    public void setData(CreateOrderModel data) {
        this.data = data;
    }
}
