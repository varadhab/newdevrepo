package com.fintech.ordersource.model;

import java.util.List;

public class PurchaseOrder {
	
	private String poNbr;
	private String deliveryDate;
	private String vendorNbr;
	private String orderStatus;
	private List<LineItem> lineItems;
	private Integer ftsId;
	
	public String getPoNbr() {
		return poNbr;
	}

	public void setPoNbr(String poNbr) {
		this.poNbr = poNbr;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public Integer getFtsId() {
		return ftsId;
	}

	public void setFtsId(Integer ftsId) {
		this.ftsId = ftsId;
	}

	@Override
	public String toString() {
		return "PurchaseOrder [poNbr=" + poNbr + ", deliveryDate=" + deliveryDate + ", vendorNbr=" + vendorNbr+ ", status=" + orderStatus
				+ ", ftsId=" + ftsId + ", lineItems=" + lineItems + "]";
	}


}
