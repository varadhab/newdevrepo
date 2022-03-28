package com.fintech.ordersource.rest.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderSourceRestErrorAdvice {
	private static final Logger logger = LoggerFactory.getLogger(OrderSourceRestErrorAdvice.class);

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception exp) {
		logger.error("Exception occured with status {} caused By{}", HttpStatus.INTERNAL_SERVER_ERROR, exp.getMessage());
	}

	@ExceptionHandler({ OrderSourceRestException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleOrderSourceRestException(OrderSourceRestException accountsRestException) {
		logger.error("Exception occured with status {} caused By{}", HttpStatus.INTERNAL_SERVER_ERROR,
				accountsRestException.getMsg());
	}
}
