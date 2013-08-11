package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnTerminologyDao;
import sk.peterjurkovic.cpr.entities.CsnTerminology;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 7, 2013
 *
 */
@Repository("csnTerminologyDao")
public class CsnTerminologyDaoImpl extends BaseDaoImpl<CsnTerminology, Long> implements CsnTerminologyDao{
	
	public CsnTerminologyDaoImpl(){
		super(CsnTerminology.class);
	}

	
	@Override
	public boolean isTitleUniqe(final Long csnId,final Long terminologyId,final String code) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM CsnTerminology ct WHERE ");
		hql.append(" ct.csn.id=:csnId AND ct.code=:code");
		if(terminologyId != null && terminologyId != 0){
			hql.append(" AND ct.id<>:terminologyId");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("code", code);
		query.setLong("csnId", csnId);
		if(terminologyId != null && terminologyId != 0){
			query.setLong("terminologyId", terminologyId);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CsnTerminology> searchInTerminology(String term) {
		StringBuilder hql = new StringBuilder("select distinct t.id, t.title from CsnTerminology t");
		hql.append(" where t.title like CONCAT('', :term , '%')");
		hql.append(" and  t.csn.enabled=true");
		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("term", term);
		hqlQuery.setMaxResults(6);
		return hqlQuery.list();
	}
	
}
