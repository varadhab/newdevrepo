package com.fintech.ordersource.model;

import java.util.List;

public class POData {

	
	private String purchaseOrderNbr;
	private String deliveryDate;
	private String vendorNumber;
	private String orderStatus;
	private String correlationId;
	private String fileName;
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
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
				+ vendorNumber + ", orderStatus=" + orderStatus + ", correlationId=" + correlationId + ", fileName="
				+ fileName + ", orderItems=" + orderItems + "]";
	}
}
