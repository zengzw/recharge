/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.resp;

import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.vas.trainticket.vo.QueryAccOrderStatusVO;

/**
 *
 * @author zengzw
 * @date 2016年12月5日
 */
public class QueryAccOrderInfoStatusResult extends BaseResponse{

    /**
     * 
     */
    private static final long serialVersionUID = -2960551122531132122L;
    
    
    private QueryAccOrderStatusVO data;

    public QueryAccOrderStatusVO getData() {
        return data;
    }

    public void setData(QueryAccOrderStatusVO data) {
        this.data = data;
    }
}
