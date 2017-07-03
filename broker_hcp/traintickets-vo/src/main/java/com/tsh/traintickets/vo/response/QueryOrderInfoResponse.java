package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class QueryOrderInfoResponse extends BaseSerializable {

    private String merchantOrderId;//	商户订单Id
    private String orderId;//	订单ID
    private OrderInfoModel orderInfo;

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderInfoModel getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoModel orderInfo) {
        this.orderInfo = orderInfo;
    }
}
