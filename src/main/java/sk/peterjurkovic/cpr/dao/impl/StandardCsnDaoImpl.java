package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardCsn> autocomplete(final String term) {
		StringBuilder hql = new StringBuilder("select csn.id, csn.csnName from StandardCsn csn");
		hql.append(" where unaccent(lower(csn.csnName)) like unaccent(:query) ");
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		return hqlQuery.setString("query",  term.toLowerCase() + "%")
				.setMaxResults(8)
				.list();
	}
	
}
