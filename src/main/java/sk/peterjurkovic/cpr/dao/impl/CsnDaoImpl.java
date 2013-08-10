package sk.peterjurkovic.cpr.dao.impl;

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
	
	
}
