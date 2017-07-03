package com.tsh.traintickets.vo;

import com.traintickets.common.BaseSerializable;

public class BaseRequest extends BaseSerializable{

	private static final long serialVersionUID = 1L;

	private String signKey;

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
}
