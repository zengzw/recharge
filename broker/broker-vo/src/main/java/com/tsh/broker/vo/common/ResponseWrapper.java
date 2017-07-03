package com.tsh.broker.vo.common;

/**
 * ResultDTO
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class ResponseWrapper<T> {

    private int status = 0;  // 返回状态
    private String message = "";  //返回消息
    private  T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
