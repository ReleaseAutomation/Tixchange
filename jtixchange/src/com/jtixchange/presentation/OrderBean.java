package com.jtixchange.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibatis.common.util.PaginatedList;
import com.jtixchange.domain.Account;
import com.jtixchange.domain.Order;
import com.jtixchange.service.AccountService;
import com.jtixchange.service.OrderService;
import com.jtixchange.struts.ActionContext;
import com.jtixchange.struts.BaseBean;

public class OrderBean extends BaseBean {

	/* Constants */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final AccountService accountService = AccountService.getInstance();
	private static final OrderService orderService = OrderService.getInstance();

	private static final List<String> CARD_TYPE_LIST;

	/* Private Fields */

	private Order order;
	private int orderId;
	private boolean shippingAddressRequired;
	private boolean confirmed;
	private PaginatedList orderList;
	private String pageDirection;

	/* Static Initializer */

	static {
		final List<String> cardList = new ArrayList<String>();
		cardList.add("Visa");
		cardList.add("MasterCard");
		cardList.add("American Express");
		CARD_TYPE_LIST = Collections.unmodifiableList(cardList);
	}

	/* Constructors */

	public OrderBean() {
		this.order = new Order();
		this.shippingAddressRequired = false;
		this.confirmed = false;
	}

	/* JavaBeans Properties */

	@Override
	public void clear() {
		this.order = new Order();
		this.orderId = 0;
		this.shippingAddressRequired = false;
		this.confirmed = false;
		this.orderList = null;
		this.pageDirection = null;
	}

	public List<String> getCreditCardTypes() {
		return OrderBean.CARD_TYPE_LIST;
	}

	public Order getOrder() {
		return this.order;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public List<?> getOrderList() {
		return this.orderList;
	}

	public String getPageDirection() {
		return this.pageDirection;
	}

	public boolean isConfirmed() {
		return this.confirmed;
	}

	public boolean isShippingAddressRequired() {
		return this.shippingAddressRequired;
	}

	public String listOrders() {
		final Map<?, ?> sessionMap = ActionContext.getActionContext().getSessionMap();
		final AccountBean accountBean = (AccountBean) sessionMap.get("accountBean");
		this.orderList = OrderBean.orderService.getOrdersByUsername(accountBean.getAccount().getUsername());
		return "success";
	}

	public String newOrder() {
		final Map<?, ?> sessionMap = ActionContext.getActionContext().getSessionMap();

		if (this.shippingAddressRequired) {
			this.shippingAddressRequired = false;
			return "shipping";
		} else if (!this.isConfirmed()) {
			return "confirm";
		} else if (this.getOrder() != null) {

			OrderBean.orderService.insertOrder(this.order);

			final CartBean cartBean = (CartBean)sessionMap.get("cartBean");
			cartBean.clear();

			ActionContext.getActionContext().setSimpleMessage("Thank you, your order has been submitted.");

			return "success";
		} else {
			ActionContext.getActionContext().setSimpleMessage("An error occurred processing your order (order was null).");
			return "failure";
		}
	}

	public String newOrderForm() {
		final Map<?, ?> sessionMap = ActionContext.getActionContext().getSessionMap();
		final AccountBean accountBean = (AccountBean) sessionMap.get("accountBean");
		final CartBean cartBean = (CartBean) sessionMap.get("cartBean");

		this.clear();
		if ((accountBean == null) || !accountBean.isAuthenticated()){
			ActionContext.getActionContext().setSimpleMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.");
			return "signon";
		} else if (cartBean != null) {
			// Re-read account from DB at team's request.
			final Account account = OrderBean.accountService.getAccount(accountBean.getAccount().getUsername());
			this.order.initOrder(account, cartBean.getCart());
			return "success";
		} else {
			ActionContext.getActionContext().setSimpleMessage("An order could not be created because a cart could not be found.");
			return "failure";
		}
	}

	/* Public Methods */

	@Override
	public void reset() {
		this.shippingAddressRequired = false;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public void setPageDirection(String pageDirection) {
		this.pageDirection = pageDirection;
	}

	public void setShippingAddressRequired(boolean shippingAddressRequired) {
		this.shippingAddressRequired = shippingAddressRequired;
	}

	public String switchOrderPage() {
		if ("next".equals(this.pageDirection)) {
			this.orderList.nextPage();
		} else if ("previous".equals(this.pageDirection)) {
			this.orderList.previousPage();
		}
		return "success";
	}

	@Override
	public void validate() {
		final ActionContext ctx = ActionContext.getActionContext();

		if (!this.isShippingAddressRequired()) {
			this.validateRequiredField(this.order.getCreditCard(), "FAKE (!) credit card number required.");
			this.validateRequiredField(this.order.getExpiryDate(), "Expiry date is required.");
			this.validateRequiredField(this.order.getCardType(), "Card type is required.");

			this.validateRequiredField(this.order.getShipToFirstName(), "Shipping Info: first name is required.");
			this.validateRequiredField(this.order.getShipToLastName(), "Shipping Info: last name is required.");
			this.validateRequiredField(this.order.getShipAddress1(), "Shipping Info: address is required.");
			this.validateRequiredField(this.order.getShipCity(), "Shipping Info: city is required.");
			this.validateRequiredField(this.order.getShipState(), "Shipping Info: state is required.");
			this.validateRequiredField(this.order.getShipZip(), "Shipping Info: zip/postal code is required.");
			this.validateRequiredField(this.order.getShipCountry(), "Shipping Info: country is required.");

			this.validateRequiredField(this.order.getBillToFirstName(), "Billing Info: first name is required.");
			this.validateRequiredField(this.order.getBillToLastName(), "Billing Info: last name is required.");
			this.validateRequiredField(this.order.getBillAddress1(), "Billing Info: address is required.");
			this.validateRequiredField(this.order.getBillCity(), "Billing Info: city is required.");
			this.validateRequiredField(this.order.getBillState(), "Billing Info: state is required.");
			this.validateRequiredField(this.order.getBillZip(), "Billing Info: zip/postal code is required.");
			this.validateRequiredField(this.order.getBillCountry(), "Billing Info: country is required.");
		}

		if (ctx.isSimpleErrorsExist()) {
			this.order.setBillAddress1(this.order.getShipAddress1());
			this.order.setBillAddress2(this.order.getShipAddress2());
			this.order.setBillToFirstName(this.order.getShipToFirstName());
			this.order.setBillToLastName(this.order.getShipToLastName());
			this.order.setBillCity(this.order.getShipCity());
			this.order.setBillCountry(this.order.getShipCountry());
			this.order.setBillState(this.order.getShipState());
			this.order.setBillZip(this.order.getShipZip());
		}

	}

	public String viewOrder() {
		final Map<?, ?> sessionMap = ActionContext.getActionContext().getSessionMap();
		final AccountBean accountBean = (AccountBean) sessionMap.get("accountBean");

		this.order = OrderBean.orderService.getOrder(this.orderId);

		if (accountBean.getAccount().getUsername().equals(this.order.getUsername())) {
			return "success";
		} else {
			this.order = null;
			ActionContext.getActionContext().setSimpleMessage("You may only view your own orders.");
			return "failure";
		}
	}

}
