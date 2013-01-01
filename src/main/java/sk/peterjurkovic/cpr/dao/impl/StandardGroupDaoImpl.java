package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.StandardGroupDao;
import sk.peterjurkovic.cpr.entities.StandardGroup;

@Repository("standardGroupDao")
public class StandardGroupDaoImpl extends BaseDaoImpl<StandardGroup, Long> implements StandardGroupDao {
	
	public StandardGroupDaoImpl(){
		super(StandardGroup.class);
	}
	
	
	/**
	 * Metoda vrati pocet noriem v danej skupine.
	 * 
	 * @param StandardGroup skupina norem
	 * @return Long pocet noriem
	 */
	@Override
	public Long getCoutOfStandardInGroup(StandardGroup standardGroup) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s");
		hql.append(" WHERE s.standardGroup.id=:id");
		return (Long) sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setLong("id", standardGroup.getId())
						.uniqueResult();

	}
	
}
