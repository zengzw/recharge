package com.traintickets.common;

public class BizException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	private ResponseCode errorCode;

    private String notice;
	
	public BizException(ResponseCode errorCode)
    {
        this.errorCode = errorCode;
    }

    public BizException(ResponseCode errorCode, String notice)
    {
        this.errorCode = errorCode;
        this.notice = notice;
    }

    public ResponseCode getCode()
    {
        return errorCode;
    }

    public String getNotice() {
        return notice;
    }

    @Override
    public String getMessage()
    {
        return String.format("code : %s ; msg : %s", errorCode.getCode(), errorCode.getMsg());
    }


}
