package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.CacheRegion;
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
	public Long getCoutOfStandardInGroup(final StandardGroup standardGroup) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s");
		hql.append(" WHERE :id in elements(s.standardGroups)");
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
		return (result == 0);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardGroup> getStandardGroupsForPublic(){
		return sessionFactory.getCurrentSession()
				.createQuery("from StandardGroup sg where sg.enabled=true order by sg.id")
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.list();
	}


	@Override
	public StandardGroup findByMandateAndCommissionDecision(String mandateName, String cdName) {
		StringBuilder hql = new StringBuilder("select sg from StandardGroup sg");
		hql.append(" left join sg.standardGroupMandates as sgm ");
		hql.append(" WHERE sgm.mandate.mandateName=:mandateName ");
		hql.append(" AND sg.commissionDecision.czechLabel=:cdName");
		
		return (StandardGroup)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setString("mandateName", mandateName)
						.setString("cdName", cdName)
						.uniqueResult();
		
	}
	
}
