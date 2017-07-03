package com.tsh.traintickets.vo.response;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/12/2 002.
 */
public class ChargingRefundResponse extends BaseSerializable {
    private String refundableAmount;

    public String getRefundableAmount() {
        return refundableAmount;
    }

    public void setRefundableAmount(String refundableAmount) {
        this.refundableAmount = refundableAmount;
    }
}
