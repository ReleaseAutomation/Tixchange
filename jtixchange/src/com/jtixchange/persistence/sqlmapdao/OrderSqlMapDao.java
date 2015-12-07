package com.jtixchange.persistence.sqlmapdao;

import java.util.UUID;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.LineItem;
import com.jtixchange.domain.Order;
import com.jtixchange.persistence.iface.OrderDao;

public class OrderSqlMapDao extends BaseSqlMapDao implements OrderDao {

	private UUID orderGUID;
	private int orderId;

	public OrderSqlMapDao(DaoManager daoManager) {
		super(daoManager);
	}

	public int getNewOrderId() {
		int testOrderId = 0;
		do {
			this.orderGUID = UUID.randomUUID();
			this.orderId = this.orderGUID.hashCode();
			testOrderId = (Integer) this.queryForObject("checkOrderNumber", new Integer(this.orderId));

		} while (testOrderId != 0);

		return this.orderId;
	}

	@SuppressWarnings("unchecked")
	public Order getOrder(int orderId) {
		Order order = null;
		final Object parameterObject = new Integer(orderId);
		order = (Order) this.queryForObject("getOrder", parameterObject);
		order.setLineItems(this.queryForList("getLineItemsByOrderId", new Integer(order.getOrderId())));
		return order;
	}

	public PaginatedList getOrdersByUsername(String username) {
		return this.queryForPaginatedList("getOrdersByUsername", username, 10);
	}

	public void insertOrder(Order order) {
		this.update("insertOrder", order);
		this.update("insertOrderStatus", order);
		for (int i = 0; i < order.getLineItems().size(); i++) {
			final LineItem lineItem = order.getLineItems().get(i);
			lineItem.setOrderId(order.getOrderId());
			this.update("insertLineItem", lineItem);
		}
	}


}
