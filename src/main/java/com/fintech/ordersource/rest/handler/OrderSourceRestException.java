package com.fintech.ordersource.rest.handler;

public class OrderSourceRestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;

	public OrderSourceRestException() {
		super();

	}

	public OrderSourceRestException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
