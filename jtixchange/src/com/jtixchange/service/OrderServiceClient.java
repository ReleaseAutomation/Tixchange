package com.jtixchange.service;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.LineItem;
import com.jtixchange.domain.Order;
import com.jtixchange.persistence.DaoConfig;
import com.jtixchange.persistence.iface.ItemDao;
import com.jtixchange.persistence.iface.OrderDao;
import com.jtixchange.persistence.iface.SequenceDao;

public class OrderServiceClient {

	/* Constants */

	private static final OrderServiceClient instance = new OrderServiceClient();

	/* Private Fields */

	public static OrderServiceClient getInstance() {
		return OrderServiceClient.instance;
	}

	private final DaoManager daoManager = DaoConfig.getDaomanager();
	private final ItemDao itemDao;
	private final OrderDao orderDao;

	/* Constructors */

	private final SequenceDao sequenceDao;

	/* Public Methods */

	public OrderServiceClient() {
		this.itemDao = (ItemDao) this.daoManager.getDao(ItemDao.class);
		this.sequenceDao = (SequenceDao) this.daoManager.getDao(SequenceDao.class);
		this.orderDao = (OrderDao) this.daoManager.getDao(OrderDao.class);
	}

	/* ORDER */

	public synchronized int getNextId() {
		return this.orderDao.getNewOrderId();
	}

	public synchronized int getNextId(String key) {
		return this.sequenceDao.getNextId(key);
	}

	public Order getOrder(int orderId) {
		Order order = null;

		try {
			this.daoManager.startTransaction();

			order = this.orderDao.getOrder(orderId);

			for (int i = 0; i < order.getLineItems().size(); i++) {
				final LineItem lineItem = order.getLineItems().get(i);
				lineItem.setItem(this.itemDao.getItem(lineItem.getItemId()));
			}

			this.daoManager.commitTransaction();
		} finally {
			this.daoManager.endTransaction();
		}

		return order;
	}

	/* SEQUENCE -- no longer used for GUIDs */

	public PaginatedList getOrdersByUsername(String username) {
		return this.orderDao.getOrdersByUsername(username);
	}

	/* Order ID from UUID */

	public void insertOrder(Order order) {
		try {
			// Get the next id within a separate transaction
			// order.setOrderId(getNextId("ordernum"));
			order.setOrderId(this.getNextId());

			this.daoManager.startTransaction();

			this.itemDao.updateQuantity(order);
			this.orderDao.insertOrder(order);

			this.daoManager.commitTransaction();
		} finally {
			this.daoManager.endTransaction();
		}
	}

}
