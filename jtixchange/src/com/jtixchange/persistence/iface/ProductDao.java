package com.jtixchange.persistence.iface;

import com.ibatis.common.util.PaginatedList;
import com.jtixchange.domain.Product;

public interface ProductDao {

	public Product getProduct(String productId);

	public PaginatedList getProductListByCategory(String categoryId);

	public PaginatedList searchProductList(String keywords);

}
