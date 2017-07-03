package com.tsh.vas.phone.commons;

public enum PhoneRefundAmountEnum {

	NON_PAYMENT("待退款", 3, "待退款"),

	PAIDING("退款中", 12, "退款中"),

	PAY_SUCCESS("退款成功", 13, "退款成功"),

	TRADING("退款失败", 15, "退款失败");

	private String desc;
	private Integer code;
	private String clientDesc;

	PhoneRefundAmountEnum(String desc, Integer code, String clientDesc) {
		this.desc = desc;
		this.code = code;
		this.clientDesc = clientDesc;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public static PhoneRefundAmountEnum getEnume(Integer code) {
		for (PhoneRefundAmountEnum item : values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		throw new IllegalArgumentException("请求参数非法");
	}
}
