package com.jtixchange.persistence.sqlmapdao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Product;
import com.jtixchange.persistence.iface.ProductDao;

public class ProductSqlMapDao extends BaseSqlMapDao implements ProductDao {

	public static class ProductSearch {
		private final List<Object> keywordList = new ArrayList<Object>();

		public ProductSearch(String keywords) {
			final StringTokenizer splitter = new StringTokenizer(keywords, " ", false);
			while (splitter.hasMoreTokens()) {
				this.keywordList.add("%" + splitter.nextToken() + "%");
			}
		}

		public List<?> getKeywordList() {
			return this.keywordList;
		}
	}

	public ProductSqlMapDao(DaoManager daoManager) {
		super(daoManager);
	}

	public Product getProduct(String productId) {
		return (Product) this.queryForObject("getProduct", productId);
	}

	public PaginatedList getProductListByCategory(String categoryId) {
		return this.queryForPaginatedList("getProductListByCategory", categoryId, BaseSqlMapDao.PAGE_SIZE);
	}

	/* Inner Classes */

	public PaginatedList searchProductList(String keywords) {
		final Object parameterObject = new ProductSearch(keywords);
		return this.queryForPaginatedList("searchProductList", parameterObject, BaseSqlMapDao.PAGE_SIZE);
	}

}
