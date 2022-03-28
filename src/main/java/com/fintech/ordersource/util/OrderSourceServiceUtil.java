package com.fintech.ordersource.util;

import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component
public class OrderSourceServiceUtil {

	/**
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public <T> String convertObjectToJsonString(T t) {
		var gson = new Gson();
		String jsonString = null;
		if (t != null) {
			jsonString = gson.toJson(t);
		}
		return jsonString;
	}
}
