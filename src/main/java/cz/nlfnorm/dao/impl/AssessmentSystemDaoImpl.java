package cz.nlfnorm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.constants.CacheRegion;
import cz.nlfnorm.dao.AssessmentSystemDao;
import cz.nlfnorm.entities.AssessmentSystem;

/**
 * Implementácia datovej vrstvy systemov hodnotenia
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("assessmentSystem")
public class AssessmentSystemDaoImpl extends BaseDaoImpl<AssessmentSystem, Long> implements AssessmentSystemDao {

	public AssessmentSystemDaoImpl() {
		super(AssessmentSystem.class);
	}
	
	
	/**
	 * Vrati zoznam publikovanych systemov posudzovania nemennosti parametrov, 
	 * @return List<AssessmentSystem> 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssessmentSystem> getAssessmentSystemsForPublic(){
		return sessionFactory.getCurrentSession()
				.createQuery("from AssessmentSystem assessmentSystem where assessmentSystem.enabled=true order by assessmentSystem.name")
				.setCacheable(true)
				.setCacheRegion(CacheRegion.CPR_CACHE)
				.list();
	}

}
