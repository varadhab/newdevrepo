package com.fintech.ordersource.model;

public class OrderItem {

	private String productName;
	private String description;
	private String quantity;
	
	
	public OrderItem() {}
	
	public OrderItem(String productName, String description, String quantity) {
		this.productName = productName;
		this.description = description;
		this.quantity = quantity;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
}
