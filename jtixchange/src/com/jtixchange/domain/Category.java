package com.jtixchange.domain;

import java.io.Serializable;


public class Category implements Serializable {

	/* Private Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId.trim();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* Public Methods */

	@Override
	public String toString() {
		return this.getCategoryId();
	}

}
