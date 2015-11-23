package com.jtixchange.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class CartItem implements Serializable {

	/* Private Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Item item;
	private int quantity;
	private boolean inStock;
	private BigDecimal total;

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

	public int getQuantity() {
		return this.quantity;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void incrementQuantity() {
		this.quantity++;
		this.calculateTotal();
	}

	public boolean isInStock() {
		return this.inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	/* Public methods */

	public void setItem(Item item) {
		this.item = item;
		this.calculateTotal();
	}

	/* Private methods */

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.calculateTotal();
	}

}
