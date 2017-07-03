package com.tsh.traintickets.vo;

import com.traintickets.common.BaseSerializable;

public abstract class BaseResponse extends BaseSerializable{

	private static final long serialVersionUID = 1L;
	
	private Integer status;
	
	private String msg;
	
	private Object data;
	
	public BaseResponse(){
		super();
	}

	public BaseResponse(Integer status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public BaseResponse(Integer status, String msg, Object data){
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
