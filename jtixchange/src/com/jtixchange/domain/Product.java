package com.jtixchange.domain;

import java.io.Serializable;


public class Product implements Serializable {

	/* Private Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId;
	private String categoryId;
	private String name;
	private String description;

	/* JavaBeans Properties */

	public String getCategoryId() {
		return this.categoryId;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductId(String productId) {
		this.productId = productId.trim();
	}

	/* Public Methods*/

	@Override
	public String toString() {
		return this.getName();
	}

}
