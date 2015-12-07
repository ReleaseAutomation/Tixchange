package com.jtixchange.persistence.iface;

import com.ibatis.common.util.PaginatedList;
import com.jtixchange.domain.Item;
import com.jtixchange.domain.Order;

public interface ItemDao {

	public Item getItem(String itemId);

	public PaginatedList getItemListByProduct(String productId);

	public boolean isItemInStock(String itemId);

	public void updateQuantity(Order order);

}
