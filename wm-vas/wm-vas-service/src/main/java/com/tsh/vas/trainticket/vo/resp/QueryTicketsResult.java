/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.resp;

import com.tsh.vas.trainticket.vo.BaseResponse;

/**
 * 火车票车次实体
 * 
 * @author zengzw
 * @date 2016年11月23日
 */
public class QueryTicketsResult extends BaseResponse {

    private QueryTicketsResponse data;

    public QueryTicketsResponse getData() {
        return data;
    }

    public void setData(QueryTicketsResponse data) {
        this.data = data;
    }


}
