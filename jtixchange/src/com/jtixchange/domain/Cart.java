package com.jtixchange.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibatis.common.util.PaginatedArrayList;
import com.ibatis.common.util.PaginatedList;

public class Cart implements Serializable {

	/* Private Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<String, CartItem>());
	private final PaginatedList itemList = new PaginatedArrayList(4);

	/* JavaBeans Properties */

	@SuppressWarnings("unchecked")
	public void addItem(Item item, boolean isInStock) {
		CartItem cartItem = this.itemMap.get(item.getItemId());
		if (cartItem == null) {
			cartItem = new CartItem();
			cartItem.setItem(item);
			cartItem.setQuantity(0);
			cartItem.setInStock(isInStock);
			this.itemMap.put(item.getItemId(), cartItem);
			this.itemList.add(cartItem);
		}
		cartItem.incrementQuantity();
	}

	public boolean containsItemId(String itemId) {
		return this.itemMap.containsKey(itemId);
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<?> getAllCartItems() {
		final List<?> allItems = new ArrayList<Object>();
		this.itemList.gotoPage(0);
		allItems.addAll(this.itemList);
		while (this.itemList.nextPage()) {
			allItems.addAll(this.itemList);
		}
		return allItems.iterator();
	}

	/* Public Methods */

	public PaginatedList getCartItemList() {
		return this.itemList;
	}

	@SuppressWarnings("unchecked")
	public Iterator<CartItem> getCartItems() {
		return this.itemList.iterator();
	}

	public int getNumberOfItems() {
		return this.itemList.size();
	}


	public BigDecimal getSubTotal() {
		BigDecimal subTotal = new BigDecimal("0");
		final Iterator<?> items = this.getAllCartItems();
		while (items.hasNext()) {
			final CartItem cartItem = (CartItem) items.next();
			final Item item = cartItem.getItem();
			final BigDecimal listPrice = item.getListPrice();
			final BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
			subTotal = subTotal.add(listPrice.multiply(quantity));
		}
		return subTotal;
	}

	public void incrementQuantityByItemId(String itemId) {
		final CartItem cartItem = this.itemMap.get(itemId);
		cartItem.incrementQuantity();
	}

	public Item removeItemById(String itemId) {
		final CartItem cartItem = this.itemMap.remove(itemId);
		if (cartItem == null) {
			return null;
		} else {
			this.itemList.remove(cartItem);
			return cartItem.getItem();
		}
	}

	public void setQuantityByItemId(String itemId, int quantity) {
		final CartItem cartItem = this.itemMap.get(itemId);
		cartItem.setQuantity(quantity);
	}

}
