package com.jtixchange.persistence.iface;

import com.ibatis.common.util.PaginatedList;
import com.jtixchange.domain.Order;

public interface OrderDao {

	public int getNewOrderId();

	public Order getOrder(int orderId);

	public PaginatedList getOrdersByUsername(String username);

	public void insertOrder(Order order);

}
