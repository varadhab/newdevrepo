package com.fintech.ordersource.model;

import java.util.List;

public class POData {

	
	private String purchaseOrderNbr;
	private String deliveryDate;
	private String vendorNumber;
	private String orderStatus;
	private List<OrderItem> orderItems;
	
	public String getPurchaseOrderNbr() {
		return purchaseOrderNbr;
	}
	public void setPurchaseOrderNbr(String purchaseOrderNbr) {
		this.purchaseOrderNbr = purchaseOrderNbr;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return "POData [purchaseOrderNbr=" + purchaseOrderNbr + ", deliveryDate=" + deliveryDate + ", vendorNumber="
				+ vendorNumber + ", orderStatus=" + orderStatus + ", orderItems=" + orderItems + "]";
	}
}
