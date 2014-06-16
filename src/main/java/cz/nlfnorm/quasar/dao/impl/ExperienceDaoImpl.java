package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.ExperienceDao;
import cz.nlfnorm.quasar.entities.Experience;

/**
 * QUASAR Component
 * 
 * @author Peter Jurkovic
 * @date Jun 17, 2014
 */
@Repository("experienceDao")
public class ExperienceDaoImpl extends BaseDaoImpl<Experience, Long> implements ExperienceDao{
	
	public ExperienceDaoImpl(){
		super(Experience.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Experience> getAllUnassignetExperiences(final Long auditorId) {
		StringBuilder hql = new StringBuilder("select exp from Experience exp ");
		hql.append(" where not EXISTS( ")
			.append(" select 1 from AuditorExperience aexp ")
			.append(" where aexp.experience.id = exp.id and aexp.auditor.id = :id )");
		return createQuery(hql)
					.setCacheable(false)
					.setLong("id", auditorId)
					.list();
	}

}
