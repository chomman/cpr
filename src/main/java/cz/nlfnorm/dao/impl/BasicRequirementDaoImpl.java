package cz.nlfnorm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.CacheRegion;
import cz.nlfnorm.dao.BasicRequirementDao;
import cz.nlfnorm.entities.BasicRequirement;

/**
 * Trieda implementujuca rozhranie sk.peterjurkovic.cpr.dao.BasicRequirementDao
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("basicRequirement")
public class BasicRequirementDaoImpl extends BaseDaoImpl<BasicRequirement, Long> implements  BasicRequirementDao  {
	
	public BasicRequirementDaoImpl(){
		super(BasicRequirement.class);
	}

	/**
	 * Skontroluje, ci nazov daneho zakladneho pozadavku je jedinecny
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@Override
	public boolean isNameUniqe(final String code,final Long id) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM BasicRequirement br");
		hql.append(" WHERE br.code=:code AND br.id<>:id");
		Long result = (Long)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setString("code", code)
						.setLong("id", id)
						.uniqueResult();
		return (result == 0);
	}
	
	/**
	 * Vrati zoznam publikvoanych zakladnych poziadavkov
	 * 
	 * @return List<BasicRequirement>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BasicRequirement> getBasicRequirementsForPublic(){
		return sessionFactory.getCurrentSession()
				.createQuery("from BasicRequirement br where br.enabled=true order by br.name")
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.list();
	}
	
}


