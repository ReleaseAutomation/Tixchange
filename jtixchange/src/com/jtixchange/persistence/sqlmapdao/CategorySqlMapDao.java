package com.jtixchange.persistence.sqlmapdao;

import java.util.List;

import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Category;
import com.jtixchange.persistence.iface.CategoryDao;

public class CategorySqlMapDao extends BaseSqlMapDao implements CategoryDao {

	public CategorySqlMapDao(DaoManager daoManager) {
		super(daoManager);
	}

	public Category getCategory(String categoryId) {
		return (Category) this.queryForObject("getCategory", categoryId);
	}

	public List<?> getCategoryList() {
		return this.queryForList("getCategoryList", null);
	}

}
