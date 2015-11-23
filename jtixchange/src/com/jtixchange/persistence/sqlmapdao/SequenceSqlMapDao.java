package com.jtixchange.persistence.sqlmapdao;

import com.ibatis.dao.client.DaoException;
import com.ibatis.dao.client.DaoManager;
import com.jtixchange.domain.Sequence;
import com.jtixchange.persistence.iface.SequenceDao;

public class SequenceSqlMapDao extends BaseSqlMapDao implements SequenceDao {

	public SequenceSqlMapDao(DaoManager daoManager) {
		super(daoManager);
	}

	/**
	 * This is a generic sequence ID generator that is based on a database
	 * table called 'SEQUENCE', which contains two columns (NAME, NEXTID).
	 * <p/>
	 * This approach should work with any database.
	 *
	 * @param name The name of the sequence.
	 * @return The Next ID
	 * @
	 */
	public synchronized int getNextId(String name) {
		Sequence sequence = new Sequence(name, -1);

		sequence = (Sequence) this.queryForObject("getSequence", sequence);
		if (sequence == null) {
			throw new DaoException("Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
		}
		final Object parameterObject = new Sequence(name, sequence.getNextId() + 1);
		this.update("updateSequence", parameterObject);

		return sequence.getNextId();
	}

}
