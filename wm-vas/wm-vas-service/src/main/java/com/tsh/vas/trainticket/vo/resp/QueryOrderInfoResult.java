/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.resp;

import com.tsh.vas.trainticket.vo.BaseResponse;

/**
 *
 * @author zengzw
 * @date 2016年11月24日
 */
public class QueryOrderInfoResult extends BaseResponse {

    private QueryOrderInfoResponse data;

    public QueryOrderInfoResponse getData() {
        return data;
    }

    public void setData(QueryOrderInfoResponse data) {
        this.data = data;
    }
    
}
