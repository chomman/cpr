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


	/**
	 * Metoda zisti, ci skupina s danym nazvom uz v systeme nachadza
	 * 
	 * @param String kod skupiny
	 * @param Long Id skupiny
	 * @return boolean TRUE ak skupina s danym nazvom neexistu, inak FALSE
	 */
	@Override
	public boolean isGroupNameUniqe(String code, Long id) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM StandardGroup sg");
		hql.append(" WHERE sg.code=:code AND sg.id<>:id");
		Long result = (Long)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setString("code", code)
						.setLong("id", id)
						.uniqueResult();
		logger.info("isNameUniqe count : " + result);
		return (result == 0);
	}
	
}
