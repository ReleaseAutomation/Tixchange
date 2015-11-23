package com.jtixchange.presentation;

import java.util.Iterator;
import java.util.Map;

import com.jtixchange.domain.Cart;
import com.jtixchange.domain.CartItem;
import com.jtixchange.domain.Item;
import com.jtixchange.service.CatalogService;
import com.jtixchange.struts.ActionContext;
import com.jtixchange.struts.BaseBean;

public class CartBean extends BaseBean {

	/* Constants */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final CatalogService catalogService = CatalogService.getInstance();

	/* Private Fields */

	private Cart cart = new Cart();
	private String workingItemId;
	private String pageDirection;

	/* JavaBeans Properties */

	public String addItemToCart() {
		if (this.cart.containsItemId(this.workingItemId)) {
			this.cart.incrementQuantityByItemId(this.workingItemId);
		} else {
			// isInStock is a "real-time" property that must be updated
			// every time an item is added to the cart, even if other
			// item details are cached.
			final boolean isInStock = CartBean.catalogService.isItemInStock(this.workingItemId);
			final Item item = CartBean.catalogService.getItem(this.workingItemId);
			this.cart.addItem(item, isInStock);
		}

		return "success";
	}

	@Override
	public void clear() {
		this.cart = new Cart();
		this.workingItemId = null;
		this.pageDirection = null;
	}

	public Cart getCart() {
		return this.cart;
	}

	public String getPageDirection() {
		return this.pageDirection;
	}

	public String getWorkingItemId() {
		return this.workingItemId;
	}

	public String removeItemFromCart() {

		final Item item = this.cart.removeItemById(this.workingItemId);

		if (item == null) {
			ActionContext.getActionContext().setSimpleMessage("Attempted to remove null CartItem from Cart.");
			return "failure";
		} else {
			return "success";
		}
	}

	/* Public Methods */

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setPageDirection(String pageDirection) {
		this.pageDirection = pageDirection;
	}

	public void setWorkingItemId(String workingItemId) {
		this.workingItemId = workingItemId;
	}

	public String switchCartPage() {
		if ("next".equals(this.pageDirection)) {
			this.cart.getCartItemList().nextPage();
		} else if ("previous".equals(this.pageDirection)) {
			this.cart.getCartItemList().previousPage();
		}
		return "success";
	}

	public String updateCartQuantities() {
		final Map<?, ?> parameterMap = ActionContext.getActionContext().getParameterMap();

		final Iterator<?> cartItems = this.getCart().getAllCartItems();
		while (cartItems.hasNext()) {
			final CartItem cartItem = (CartItem) cartItems.next();
			final String itemId = cartItem.getItem().getItemId();
			try {
				final int quantity = Integer.parseInt((String) parameterMap.get(itemId));
				this.getCart().setQuantityByItemId(itemId, quantity);
				if (quantity < 1) {
					cartItems.remove();
				}
			} catch (final Exception e) {
				//ignore on purpose
			}
		}

		return "success";
	}

	public String viewCart() {
		return "success";
	}

}
