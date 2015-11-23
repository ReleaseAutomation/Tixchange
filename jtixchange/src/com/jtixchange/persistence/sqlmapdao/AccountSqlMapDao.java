package com.jtixchange.persistence.sqlmapdao;

import java.util.List;

import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Account;
import com.jtixchange.persistence.iface.AccountDao;

public class AccountSqlMapDao extends BaseSqlMapDao implements AccountDao {

	public AccountSqlMapDao(DaoManager daoManager) {
		super(daoManager);
	}

	public Account getAccount(String username) {
		return (Account) this.queryForObject("getAccountByUsername", username);
	}

	public Account getAccount(String username, String password) {
		final Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		return (Account) this.queryForObject("getAccountByUsernameAndPassword", account);
	}

	public List<?> getUsernameList() {
		return this.queryForList("getUsernameList", null);
	}

	public void insertAccount(Account account) {
		this.update("insertAccount", account);
		this.update("insertProfile", account);
		this.update("insertSignon", account);
	}

	public void updateAccount(Account account) {
		this.update("updateAccount", account);
		this.update("updateProfile", account);

		if ((account.getPassword() != null) && (account.getPassword().length() > 0)) {
			this.update("updateSignon", account);
		}
	}


}
