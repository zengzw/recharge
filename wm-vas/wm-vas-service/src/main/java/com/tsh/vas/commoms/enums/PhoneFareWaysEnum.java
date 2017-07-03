package com.tsh.vas.commoms.enums;

/**
 * 话费充值方案状态枚举
 * @author William.Zhang
 *
 */
public enum PhoneFareWaysEnum {
	
	ALL(-1),
	
	DEL(0),
	
	NORMAL(1);
	
	PhoneFareWaysEnum(int status) {
		this.status = status;
	}
	
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
