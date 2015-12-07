package com.jtixchange.service;

import java.util.List;
import java.util.Random;

import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Account;
import com.jtixchange.persistence.DaoConfig;
import com.jtixchange.persistence.iface.AccountDao;

public class AccountService {

	/* Constants */

	private static final AccountService instance = new AccountService();

	/* Private Fields */

	public static AccountService getInstance() {
		return AccountService.instance;
	}
	private final DaoManager daoManager = DaoConfig.getDaomanager();
	private final AccountDao accountDao;

	/* Constructors */

	private final Random randomNum = new Random();

	/* Public Methods */

	public AccountService() {
		this.accountDao = (AccountDao) this.daoManager.getDao(AccountDao.class);
	}

	/* ACCOUNT */

	public Account getAccount(String username) {
		return this.accountDao.getAccount(username);
	}

	public Account getAccountWithPass(String username, String password) {
		return this.accountDao.getAccount(username, password);
	}

	public Account getAuthAccount(String username, String password) {
		return this.accountDao.getAccount(username, password);
	}

	public Account getAuthAccountWild(String username, String password) {
		Account returnAccount = null;
		int paperCuts = this.randomNum.nextInt(2000);
		if (paperCuts < 1000) {
			paperCuts += 1000;
		}
		for (int i=0; i<paperCuts; i++) {
			returnAccount = this.accountDao.getAccount(username, password);
		}
		return returnAccount;
	}

	public List<?> getUsernameList() {
		return this.accountDao.getUsernameList();
	}

	public void insertAccount(Account account) {
		this.accountDao.insertAccount(account);
	}

	public void updateAccount(Account account) {
		this.accountDao.updateAccount(account);
	}

}
