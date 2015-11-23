package com.jtixchange.persistence.iface;

import java.util.List;

import com.jtixchange.domain.Account;

public interface AccountDao {

	public Account getAccount(String username);

	public Account getAccount(String username, String password);

	public List<?> getUsernameList();

	public void insertAccount(Account account);

	public void updateAccount(Account account);

}
