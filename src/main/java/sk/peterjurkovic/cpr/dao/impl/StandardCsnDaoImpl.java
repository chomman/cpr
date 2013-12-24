package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.StandardCsnDao;
import sk.peterjurkovic.cpr.entities.StandardCsn;

/**
 * Implementacia rozhrania sk.peterjurkovic.cpr.dao.impl.StandardCsnDao
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("standardCsnDao")
public class StandardCsnDaoImpl extends BaseDaoImpl<StandardCsn, Long> implements StandardCsnDao{
	
	
	public StandardCsnDaoImpl(){
		super(StandardCsn.class);
	}

	@Override
	public StandardCsn getByCatalogNo(final String catalogNumber) {
		return (StandardCsn)sessionFactory.getCurrentSession()
				.createQuery("FROM StandardCsn csn WHERE csn.csnOnlineId=:csnOnlineId ")
				.setString("csnOnlineId", catalogNumber.trim())
				.setMaxResults(1)
				.uniqueResult();
	}
	
}
