/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.phone.recharge.rtpay.parse.resp;

/**
 *
 * @author zengzw
 * @date 2017年5月2日
 */
public class BaseResponse<T> {


    /**
     * 返回对象类型
     */
    private T data;

    /**
     * 返回状态
     */
    private String status;

    /**
     * 扩展字段
     */
    private String throwable;

    /**
     * 错误消息
     */
    private String msg;

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public String getThrowable() {
        return throwable;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

}
