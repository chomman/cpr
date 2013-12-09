package sk.peterjurkovic.cpr.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.StandardGroupMandateDao;
import sk.peterjurkovic.cpr.entities.StandardGroupMandate;
import sk.peterjurkovic.cpr.services.StandardGroupMandateService;


@Service("standardGroupMandateService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardGroupMandateServiceImpl implements StandardGroupMandateService {
	
	@Autowired
	private StandardGroupMandateDao standardGroupMandateDao;
	
	@Override
	public void create(StandardGroupMandate standardGroupMandate) {
		standardGroupMandateDao.save(standardGroupMandate);
	}

	@Override
	public void delete(StandardGroupMandate standardGroupMandate) {
		standardGroupMandateDao.remove(standardGroupMandate);
	}

	@Override
	public void update(StandardGroupMandate standardGroupMandate) {
		standardGroupMandateDao.update(standardGroupMandate);
	}

	@Override
	@Transactional(readOnly = true)
	public StandardGroupMandate getById(final Long id) {
		return standardGroupMandateDao.getByID(id);
	}
	
	
	
}
