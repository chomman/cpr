package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.MandateDao;
import sk.peterjurkovic.cpr.entities.Mandate;


@Repository("mandateDao")
public class MandateDaoImpl extends BaseDaoImpl<Mandate, Long> implements MandateDao {
	
	public MandateDaoImpl(){
		super(Mandate.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Mandate> getMandatePage(int pageNumber) {
		logger.debug("Listing in mandates "+ pageNumber);
		List<Mandate> mandates = new ArrayList<Mandate>();
		
		mandates = (List<Mandate>)  sessionFactory.getCurrentSession()
				.createQuery("from Mandate")
				.setFirstResult(Constants.PAGINATION_PAGE_SIZE * pageNumber -1)
				.setMaxResults(Constants.PAGINATION_PAGE_SIZE)
				.list();
		return mandates;
	}
	
	
}
