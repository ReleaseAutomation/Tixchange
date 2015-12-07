package com.jtixchange.persistence.sqlmapdao;

import java.util.HashMap;
import java.util.Map;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Item;
import com.jtixchange.domain.LineItem;
import com.jtixchange.domain.Order;
import com.jtixchange.persistence.iface.ItemDao;

public class ItemSqlMapDao extends BaseSqlMapDao implements ItemDao {

	public ItemSqlMapDao(DaoManager daoManager) {
		super(daoManager);
	}

	public Item getItem(String itemId) {
		final Integer i = (Integer) this.queryForObject("getInventoryQuantity", itemId);
		final Item item = (Item) this.queryForObject("getItem", itemId);
		item.setQuantity(i.intValue());
		return item;
	}

	public PaginatedList getItemListByProduct(String productId) {
		return this.queryForPaginatedList("getItemListByProduct", productId, BaseSqlMapDao.PAGE_SIZE);
	}

	public boolean isItemInStock(String itemId) {
		final Integer i = (Integer) this.queryForObject("getInventoryQuantity", itemId);
		return ((i != null) && (i.intValue() > 0));
	}

	public void updateQuantity(Order order) {
		for (int i = 0; i < order.getLineItems().size(); i++) {
			final LineItem lineItem = order.getLineItems().get(i);
			final String itemId = lineItem.getItemId();
			final Integer increment = new Integer(lineItem.getQuantity());
			@SuppressWarnings("rawtypes")
			final Map<String, Comparable> param = new HashMap<String, Comparable>(2);
			param.put("itemId", itemId);
			param.put("increment", increment);
			this.update("updateInventoryQuantity", param);
		}
	}

}
