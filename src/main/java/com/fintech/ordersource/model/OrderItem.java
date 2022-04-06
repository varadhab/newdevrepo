package com.fintech.ordersource.model;

public class OrderItem {

	private String vendorProductNbr;
	private String orderQuantity;
	private String unitOfMeasure;
	private String productName;
	private String unitPrice;
	private double invoiceTotal;
	
	
	public OrderItem() {}
	

	public String getVendorProductNbr() {
		return vendorProductNbr;
	}


	public void setVendorProductNbr(String vendorProductNbr) {
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


	public double getInvoiceTotal() {
		return invoiceTotal;
	}


	public void setInvoiceTotal(double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}
	
	
}
