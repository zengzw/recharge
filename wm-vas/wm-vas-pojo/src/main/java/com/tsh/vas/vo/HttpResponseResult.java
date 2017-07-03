/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.vo;

import com.dtds.platform.util.bean.Result;

/**
 *
 * @author zengzw
 * @date 2017年6月22日
 */
public class HttpResponseResult {

    private String msg ;
    
    private int status;
    
    private Object data;
    
    private String errorMsg;
    
    private int code;// 日志编码
    
    private String codeMsg;// 日志编码消息
    
    
    public boolean hasError(){
        return (status != Result.STATUS_OK);
    }

    public int getCode() {
        return code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public Object getData() {
        return data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
