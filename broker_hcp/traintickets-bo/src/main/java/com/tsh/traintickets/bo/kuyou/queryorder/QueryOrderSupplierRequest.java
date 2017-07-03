package com.tsh.traintickets.bo.kuyou.queryorder;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/21 021.
 */
public class QueryOrderSupplierRequest extends BaseSerializable {

    private String order_id;//	订单ID
    private String merchant_order_id;//	商户订单ID

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public void setMerchant_order_id(String merchant_order_id) {
        this.merchant_order_id = merchant_order_id;
    }
}
