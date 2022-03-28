package com.fintech.ordersource.util;

public enum OrderSourceApiErrorEnum {

	ORDER_SOURCE_API_ERROR("ORDERSOURCE-ERR-101", "Error occurred while invoking Order Source API");
	
	private final String code;
	private final String description;

	private OrderSourceApiErrorEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
