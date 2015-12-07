package com.jtixchange.presentation;

import com.ibatis.common.util.PaginatedList;
import com.jtixchange.domain.Category;
import com.jtixchange.domain.Item;
import com.jtixchange.domain.Product;
import com.jtixchange.service.CatalogService;
import com.jtixchange.struts.ActionContext;
import com.jtixchange.struts.BaseBean;

public class CatalogBean extends BaseBean {

	/* Constants */

	private static final long serialVersionUID = 1L;
	private static final CatalogService catalogService = CatalogService.getInstance();
	private static final long slowDownTime = 800;

	/* Private Fields */

	private String keyword;
	private String pageDirection;

	private String categoryId;
	private Category category;
	private PaginatedList categoryList;

	private String productId;
	private Product product;
	private PaginatedList productList;

	private String itemId;
	private Item item;
	private PaginatedList itemList;
	
	/* Public Methods */
	
	@Override
	public void clear () {
		this.keyword = null;
		this.pageDirection = null;

		this.categoryId = null;
		this.category = null;
		this.categoryList = null;

		this.productId = null;
		this.product = null;
		this.productList = null;

		this.itemId = null;
		this.item = null;
		this.itemList = null;
	}

	public Category getCategory() {
		return this.category;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public PaginatedList getCategoryList() {
		return this.categoryList;
	}

	public Item getItem() {
		return this.item;
	}

	public String getItemId() {
		return this.itemId;
	}

	public PaginatedList getItemList() {
		return this.itemList;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public String getPageDirection() {
		return this.pageDirection;
	}

	public Product getProduct() {
		return this.product;
	}

	public String getProductId() {
		return this.productId;
	}

	public PaginatedList getProductList() {
		return this.productList;
	}

	public String searchProducts() {
		if ((this.keyword == null) || (this.keyword.length() < 1)) {
			ActionContext.getActionContext().setSimpleMessage("Please enter a keyword to search for, then press the search button.");
			return "failure";
		} else {
			this.slowdownThread();
			this.productList = CatalogBean.catalogService.searchProductList(this.keyword.toLowerCase());
			return "success";
		}
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryList(PaginatedList categoryList) {
		this.categoryList = categoryList;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemList(PaginatedList itemList) {
		this.itemList = itemList;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setPageDirection(String pageDirection) {
		this.pageDirection = pageDirection;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductList(PaginatedList productList) {
		this.productList = productList;
	}

	public void slowdownThread() {
		try {
			Thread.sleep(CatalogBean.slowDownTime);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String switchItemListPage() {
		if ("next".equals(this.pageDirection)) {
			this.itemList.nextPage();
		} else if ("previous".equals(this.pageDirection)) {
			this.itemList.previousPage();
		}
		return "success";
	}

	public String switchProductListPage() {
		if ("next".equals(this.pageDirection)) {
			this.productList.nextPage();
		} else if ("previous".equals(this.pageDirection)) {
			this.productList.previousPage();
		}
		return "success";
	}

	public String viewCategory() {
		if (this.categoryId != null) {
			this.slowdownThread();
			this.productList = CatalogBean.catalogService.getProductListByCategory(this.categoryId);
			this.category = CatalogBean.catalogService.getCategory(this.categoryId);
		}
		return "success";
	}

	public String viewItem() {
		this.slowdownThread();
		this.item = CatalogBean.catalogService.getItem(this.itemId);
		this.product = this.item.getProduct();
		return "success";
	}

	public String viewProduct() {
		if (this.productId != null) {
			this.slowdownThread();
			this.itemList = CatalogBean.catalogService.getItemListByProduct(this.productId);
			this.product = CatalogBean.catalogService.getProduct(this.productId);
		}
		return "success";
	}
}