package sk.peterjurkovic.cpr.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.DeclarationOfPerformanceDao;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;

@Repository("declarationOfPerformanceDao")
public class DeclarationOfPerformanceDaoImpl 
		extends BaseDaoImpl<DeclarationOfPerformance, Long> 
		implements DeclarationOfPerformanceDao{
	
	
	public DeclarationOfPerformanceDaoImpl(){
		super(DeclarationOfPerformance.class);
	}

	
	@Override
	public DeclarationOfPerformance getByToken(String token) {
		return (DeclarationOfPerformance) sessionFactory.getCurrentSession()
				.createCriteria(DeclarationOfPerformance.class)
				.add( Restrictions.eq("token", token) )
				.setMaxResults(1)
				.uniqueResult();
		
	}


	@Override
	public void deleteEssentialCharacteristicByDopId(Long id) {
		sessionFactory.getCurrentSession()
			.createQuery("delete from EssentialCharacteristic ch where ch.declarationOfPerformance.id=:id")
			.setLong("id", id)
			.executeUpdate();
	}
}
