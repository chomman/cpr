package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.BasicRequirementDao;
import sk.peterjurkovic.cpr.entities.BasicRequirement;

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
	
}

