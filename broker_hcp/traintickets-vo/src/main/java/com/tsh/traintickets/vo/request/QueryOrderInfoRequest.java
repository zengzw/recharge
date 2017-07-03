package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class QueryOrderInfoRequest extends BaseRequest{

    private String orderId;//	订单ID
    private String merchantOrderId;//	商户订单ID

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }
}
