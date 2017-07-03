/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo.resp;

import java.io.Serializable;

import com.tsh.vas.trainticket.vo.CheckTicketNumModel;

/**
 *
 * @author zengzw
 * @date 2016年11月24日
 */
public class QueryTicketNumResult implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3948525273944117039L;

    private CheckTicketNumModel data;

    public CheckTicketNumModel getData() {
        return data;
    }

    public void setData(CheckTicketNumModel data) {
        this.data = data;
    }
}
