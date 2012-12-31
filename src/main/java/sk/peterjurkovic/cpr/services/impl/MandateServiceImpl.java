package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.MandateDao;
import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.services.MandateService;

@Service("mandateService")
@Transactional(propagation = Propagation.REQUIRED)
public class MandateServiceImpl implements MandateService {
	
	@Autowired
	private MandateDao mandateDao;
	
	@Override
	public void createMandate(Mandate mandate) {
		mandateDao.save(mandate);
	}

	@Override
	public void updateMandate(Mandate mandate) {
		mandateDao.update(mandate);
	}

	@Override
	public void deleteMandate(Mandate mandate) {
		mandateDao.remove(mandate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Mandate> getAllMandates() {
		return mandateDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Mandate getMandateById(Long id) {
		return mandateDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Mandate getMandateByCode(String code) {
		return mandateDao.getByCode(code);
	}

}
