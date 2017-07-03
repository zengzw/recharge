package com.tsh.traintickets.vo;

import com.traintickets.common.ResponseCode;

public class ResponseBuilder extends BaseResponse{

	private static final long serialVersionUID = 1L;

	public ResponseBuilder(Integer code, String msg) {
		super(code, msg);
	}

	public ResponseBuilder(Integer code, String msg, Object data) {
		super(code, msg, data);
	}
	
	public static BaseResponse buildSuccess(){
		return new ResponseBuilder(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
	}
	
	public static BaseResponse buildSuccess(Object data){
		return new ResponseBuilder(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
	}

	public static BaseResponse buildError(ResponseCode code){
		return new ResponseBuilder(code.getCode(), code.getMsg());
	}

	public static BaseResponse buildError(ResponseCode code, Object data){
		return new ResponseBuilder(code.getCode(), code.getMsg(), data);
	}

	public static BaseResponse buildError(ResponseCode code, String notice){
		return new ResponseBuilder(code.getCode(), code.getMsg() + ", " + notice);
	}
}
