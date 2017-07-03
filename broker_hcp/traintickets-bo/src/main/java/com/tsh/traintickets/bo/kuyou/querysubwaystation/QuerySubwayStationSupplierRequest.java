package com.tsh.traintickets.bo.kuyou.querysubwaystation;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class QuerySubwayStationSupplierRequest extends BaseSerializable {

    private String train_code;

    public String getTrain_code() {
        return train_code;
    }

    public void setTrain_code(String train_code) {
        this.train_code = train_code;
    }
}
