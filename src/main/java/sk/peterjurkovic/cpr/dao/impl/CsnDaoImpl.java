package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.CsnDao;
import sk.peterjurkovic.cpr.entities.Csn;


@Repository("csnDao")
public class CsnDaoImpl extends BaseDaoImpl<Csn, Long> implements CsnDao{
	
	public CsnDaoImpl(){
		super(Csn.class);
	}

	@Override
	public boolean isCsnIdUniqe(final Long id,final String csnId) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Csn c WHERE c.csnId=:csnId");
		if(id != null && id != 0){
			hql.append(" AND c.id<>:id");
		}
		Long result = null;
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("csnId", csnId);
		if(id != null && id != 0){
			query.setLong("id", id);
		}
		result = (Long)query.uniqueResult();
		return (result == 0);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Csn> getCsnByTerminology(String terminologyTitle) {
		StringBuilder hql = new StringBuilder("select t from CsnTerminology t ");
		hql.append(" where t.csn.enabled=true and t.title like CONCAT('%', :terminologyTitle , '%')");
		Query query =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("terminologyTitle", terminologyTitle);
		query.setMaxResults(50);
		return query.list();
	}
	
	
}
