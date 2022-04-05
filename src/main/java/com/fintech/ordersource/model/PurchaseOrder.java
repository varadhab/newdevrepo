package com.fintech.ordersource.model;

public class PurchaseOrder {
	
	private String orderId;
	private String productName;
	private String description;
	private String quantity;
	private String orderStatus;
	

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	
	@Override
	public String toString() {
		return "PurchaseOrder [orderId=" + orderId + ", productName=" + productName + ", "
				+ "description=" + description+ ", orderStatus=" + orderStatus
				+ ", quantity=" + quantity + "]";
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
