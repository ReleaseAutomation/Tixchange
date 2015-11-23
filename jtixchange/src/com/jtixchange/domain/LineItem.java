package com.jtixchange.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class LineItem implements Serializable {

	/* Private Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderId;
	private int lineNumber;
	private int quantity;
	private String itemId;
	private BigDecimal unitPrice;
	private Item item;
	private BigDecimal total;

	/* Constructors */

	public LineItem() {
	}

	public LineItem(int lineNumber, CartItem cartItem) {
		this.lineNumber = lineNumber;
		this.quantity = cartItem.getQuantity();
		this.itemId = cartItem.getItem().getItemId();
		this.unitPrice = cartItem.getItem().getListPrice();
		this.item = cartItem.getItem();
	}

	/* JavaBeans Properties */

	private void calculateTotal() {
		if ((this.item != null) && (this.item.getListPrice() != null)) {
			this.total = this.item.getListPrice().multiply(new BigDecimal(this.quantity));
		} else {
			this.total = null;
		}
	}

	public Item getItem() {
		return this.item;
	}

	public String getItemId() {
		return this.itemId;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setItem(Item item) {
		this.item = item;
		this.calculateTotal();
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.calculateTotal();
	}

	/* Private methods */

	public void setUnitPrice(BigDecimal unitprice) {
		this.unitPrice = unitprice;
	}


}
