package com.jtixchange.persistence.iface;

import java.util.List;

import com.jtixchange.domain.Category;

public interface CategoryDao {

	public Category getCategory(String categoryId);

	public List<?> getCategoryList();

}
