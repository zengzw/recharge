/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.vo;

/** 
 *
 * 查询订单状态实体VO
 * 
 * @author zengzw
 * @date 2016年12月5日
 */
public class QueryAccOrderStatusVO {

    /**
     * 订单好
     */
    private String orderNo;

    /**
     * 交易状态
     */
    private int status;

    /**
     * 错误信息
     */
    private String msg;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
