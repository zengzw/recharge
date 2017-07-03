package com.tsh.phone.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  返回对象
 *
 * @author zengzw
 * @date 2017年2月21日
 */
public class ResponseResult{


    private int status = 200;  // 返回状态

    private String message = "";  //返回消息
    
    private String msg;
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String code = "0"; //业务代码
      
    private  Object data;

    public ResponseResult(){}
    
    public ResponseResult(int status,String msg){
        this.status = status;
        this.message = msg;
    }
    public ResponseResult(int status,String msg,String code){
        this.status = status;
        this.message = msg;
        this.code = code;
    }

    public ResponseResult(Object data){
        this.data = data;
    }

    public String getCode() {
        return code;
    }



    public Object getData() {
        return data == null ? "" : data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    @JSONField(serialize=false)
    public boolean hasError(){
        return (this.status != 200);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
}
