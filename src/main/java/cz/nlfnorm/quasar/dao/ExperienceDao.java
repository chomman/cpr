package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.Experience;

/**
 * QUASAR Component
 * 
 * @author Peter Jurkovic
 * @date Jun 17, 2014
 */
public interface ExperienceDao extends BaseDao<Experience, Long>{

	List<Experience> getAllUnassignetExperiences(Long auditorId);
}
