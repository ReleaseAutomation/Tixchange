package com.jtixchange.service;

import java.util.List;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Category;
import com.jtixchange.domain.Item;
import com.jtixchange.domain.Product;
import com.jtixchange.persistence.DaoConfig;
import com.jtixchange.persistence.iface.CategoryDao;
import com.jtixchange.persistence.iface.ItemDao;
import com.jtixchange.persistence.iface.ProductDao;

public class CatalogService {

	/* Constants */

	private static final CatalogService instance = new CatalogService();

	/* Private Fields */

	public static CatalogService getInstance() {
		return CatalogService.instance;
	}

	private final DaoManager daoManager = DaoConfig.getDaomanager();
	private final CategoryDao categoryDao;
	private final ItemDao itemDao;

	/* Constructors */

	private final ProductDao productDao;

	/* Public Methods */

	private CatalogService() {
		this.categoryDao = (CategoryDao) this.daoManager.getDao(CategoryDao.class);
		this.productDao = (ProductDao) this.daoManager.getDao(ProductDao.class);
		this.itemDao = (ItemDao) this.daoManager.getDao(ItemDao.class);
	}

	/* CATEGORY */

	public Category getCategory(String categoryId) {
		return this.categoryDao.getCategory(categoryId);
	}

	public List<?> getCategoryList() {
		return this.categoryDao.getCategoryList();
	}

	/* PRODUCT */

	public Item getItem(String itemId) {
		return this.itemDao.getItem(itemId);
	}

	public PaginatedList getItemListByProduct(String productId) {
		return this.itemDao.getItemListByProduct(productId);
	}

	public Product getProduct(String productId) {
		return this.productDao.getProduct(productId);
	}

	/* ITEM */

	public PaginatedList getProductListByCategory(String categoryId) {
		return this.productDao.getProductListByCategory(categoryId);
	}

	public boolean isItemInStock(String itemId) {
		return this.itemDao.isItemInStock(itemId);
	}

	public PaginatedList searchProductList(String keywords) {
		return this.productDao.searchProductList(keywords);
	}

}