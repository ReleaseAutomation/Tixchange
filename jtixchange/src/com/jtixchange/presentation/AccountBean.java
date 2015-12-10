package com.jtixchange.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ibatis.common.util.PaginatedList;
import com.jtixchange.domain.Account;
import com.jtixchange.service.AccountServiceClient;
import com.jtixchange.service.CatalogService;
import com.jtixchange.struts.ActionContext;
import com.jtixchange.struts.BaseBean;
import com.jtixchange.struts.BeanActionException;

public class AccountBean extends BaseBean {

	/* Constants */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final AccountServiceClient accountService = AccountServiceClient.getInstance();
	private static final CatalogService catalogService = CatalogService.getInstance();

	private static final String VALIDATE_NEW_ACCOUNT = "new";
	private static final String VALIDATE_EDIT_ACCOUNT = "edit";

	private static final List<String> LANGUAGE_LIST;
	private static final List<String> CATEGORY_LIST;
	private static final List<String> SIGNON_LIST;

	/* Private Fields */

	private Account account;
	private String repeatedPassword;
	private String signOnType;
	private String pageDirection;
	private String validation;
	private PaginatedList myList;
	private boolean authenticated;

	/* Static Initializer */

	static {
		final List<String> langList = new ArrayList<String>();
		langList.add("english");
		langList.add("japanese");
	
		LANGUAGE_LIST = Collections.unmodifiableList(langList);

		final List<String> catList = new ArrayList<String>();
		catList.add("CONCERTS");
		catList.add("CONFERENCES");
		catList.add("FESTIVALS");
		catList.add("SPORTS");
		catList.add("THEATER");
		CATEGORY_LIST = Collections.unmodifiableList(catList);

		final List<String> signOnList = new ArrayList<String>();
		signOnList.add("Normal");
		signOnList.add("Wildcard");
		SIGNON_LIST = Collections.unmodifiableList(signOnList);
	}

	/* Constructors */

	public AccountBean() {
		this.account = new Account();
	}

	/* JavaBeans Properties */

	@Override
	public void clear() {
		this.account = new Account();
		this.repeatedPassword = null;
		this.pageDirection = null;
		this.myList = null;
		this.authenticated = false;
	}

	public String editAccount() {
		try {
			AccountBean.accountService.updateAccount(this.account);
			this.account = AccountBean.accountService.getAccount(this.account.getUsername());
			this.myList = AccountBean.catalogService.getProductListByCategory(this.account.getFavouriteCategoryId());
			return "success";
		} catch (final Exception e) {
			throw new BeanActionException ("There was a problem updating your Account Information. Cause: "+e, e);
		}
	}

	public String editAccountForm() {
		try {
			this.account = AccountBean.accountService.getAccount(this.account.getUsername());
			return "success";
		} catch (final Exception e) {
			throw new BeanActionException ("There was a problem retrieving your Account Information. Cause: "+e, e);
		}
	}

	public Account getAccount() {
		return this.account;
	}

	public List<String> getCategories() {
		return AccountBean.CATEGORY_LIST;
	}

	public List<String> getLanguages() {
		return AccountBean.LANGUAGE_LIST;
	}

	public PaginatedList getMyList() {
		return this.myList;
	}

	public String getPageDirection() {
		return this.pageDirection;
	}

	public String getPassword() {
		return this.account.getPassword();
	}

	public String getRepeatedPassword() {
		return this.repeatedPassword;
	}

	public String getSignOnType() {
		return this.signOnType;
	}

	public List<String> getSignOnTypes() {
		return AccountBean.SIGNON_LIST;
	}


	public String getUsername() {
		return this.account.getUsername();
	}

	public String getValidation() {
		return this.validation;
	}

	public boolean isAuthenticated() {
		return this.authenticated && (this.account != null) && (this.account.getUsername() != null);
	}

	public String newAccount() {
		try {
			AccountBean.accountService.insertAccount(this.account);
			this.account = AccountBean.accountService.getAccount(this.account.getUsername());
			this.myList = AccountBean.catalogService.getProductListByCategory(this.account.getFavouriteCategoryId());
			this.authenticated = true;
			this.repeatedPassword = null;
			return "success";
		} catch (final Exception e) {
			throw new BeanActionException ("There was a problem creating your Account Information.  Cause: " + e, e);
		}
	}

	@Override
	public void reset() {
		if (this.account != null) {
			this.account.setBannerOption(false);
			this.account.setListOption(false);
		}
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setMyList(PaginatedList myList) {
		this.myList = myList;
	}

	/* Public Methods */

	public void setPageDirection(String pageDirection) {
		this.pageDirection = pageDirection;
	}

	public void setPassword(String password) {
		this.account.setPassword(password);
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public void setSignOnType(String signOnType) {
		this.signOnType = signOnType;
	}

	public void setUsername(String username) {
		this.account.setUsername(username);
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String signoff() {
		ActionContext.getActionContext().getRequest().getSession().invalidate();
		this.clear();
		return "success";
	}

	public String signon() {
		try {
			if (!this.signOnType.equals(null)) {
				if (this.signOnType.equals("Wildcard")) {
					this.account = AccountBean.accountService.getAuthAccountWild(this.account.getUsername(), this.account.getPassword());
				} else {
					this.account = AccountBean.accountService.getAuthAccount(this.account.getUsername(), this.account.getPassword());
				}
			} else {
				this.account = AccountBean.accountService.getAuthAccount(this.account.getUsername(), this.account.getPassword());
			}

		} catch (final Exception e) {
			throw new BeanActionException ("There was a problem updating your Account Information. Cause: "+e, e);
		}

		if ((this.account == null) || (this.account == null)) {
			ActionContext.getActionContext().setSimpleMessage("Invalid username or password. Signon failed.");
			this.clear();
			return "failure";
		} else {
			this.account.setPassword(null);
			this.myList = AccountBean.catalogService.getProductListByCategory(this.account.getFavouriteCategoryId());
			this.authenticated = true;
			return "success";
		}
	}

	public String switchMyListPage () {
		if ("next".equals(this.pageDirection)) {
			this.myList.nextPage();
		} else if ("previous".equals(this.pageDirection)) {
			this.myList.previousPage();
		}
		return "success";
	}

	@Override
	public void validate() {
		final ActionContext ctx = ActionContext.getActionContext();
		if (this.validation != null) {
			if (AccountBean.VALIDATE_EDIT_ACCOUNT.equals(this.validation) || AccountBean.VALIDATE_NEW_ACCOUNT.equals(this.validation)) {
				if (AccountBean.VALIDATE_NEW_ACCOUNT.equals(this.validation)) {
					this.account.setStatus("OK");
					this.validateRequiredField(this.account.getUsername(), "User ID is required.");
					if ((this.account.getPassword() == null) || (this.account.getPassword().length() < 1) || !this.account.getPassword().equals(this.repeatedPassword)) {
						ctx.addSimpleError("Passwords did not match or were not provided.  Matching passwords are required.");
					}
				}
				if ((this.account.getPassword() != null) && (this.account.getPassword().length() > 0)) {
					if (!this.account.getPassword().equals(this.repeatedPassword)) {
						ctx.addSimpleError("Passwords did not match.");
					}
				}
				this.validateRequiredField(this.account.getFirstName(), "First name is required.");
				this.validateRequiredField(this.account.getLastName(), "Last name is required.");
				this.validateRequiredField(this.account.getEmail(), "Email address is required.");
				this.validateRequiredField(this.account.getPhone(), "Phone number is required.");
				this.validateRequiredField(this.account.getAddress1(), "Address (1) is required.");
				this.validateRequiredField(this.account.getCity(), "City is required.");
				this.validateRequiredField(this.account.getState(), "State is required.");
				this.validateRequiredField(this.account.getZip(), "ZIP is required.");
				this.validateRequiredField(this.account.getCountry(), "Country is required.");
			}
		}

	}

}
