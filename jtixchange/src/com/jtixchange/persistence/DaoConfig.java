package com.jtixchange.persistence;

import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.DaoManagerBuilder;

public class DaoConfig {

	private static final DaoManager daoManager;

	static {

		try {
			final String resource = "com/jtixchange/persistence/dao.xml";
			final Reader reader = Resources.getResourceAsReader(resource);
			daoManager = DaoManagerBuilder.buildDaoManager(reader);
		} catch (final Exception e) {
			throw new RuntimeException("Could not initialize DaoConfig.  Cause: " + e);
		}
	}

	public static DaoManager getDaomanager() {
		return DaoConfig.daoManager;
	}

}
