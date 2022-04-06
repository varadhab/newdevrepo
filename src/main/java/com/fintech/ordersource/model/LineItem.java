package com.fintech.ordersource.model;

public class LineItem {

	private String vendorProductNbr;
	private String orderQuantity;
	private String unitOfMeasure;
	private String productName;
	private String unitPrice;
	
	public String getVendorProductNbr() {
		return vendorProductNbr;
	}
	public void setVendorProductbr(String vendorProductNbr) {
		this.vendorProductNbr = vendorProductNbr;
	}
	public String getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
}
