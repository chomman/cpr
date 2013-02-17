package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.DeclarationOfPerformanceDao;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.services.DeclarationOfPerformanceService;


@Service("declarationOfPerformance")
@Transactional(propagation = Propagation.REQUIRED)
public class DeclarationOfPerformanceServiceImpl implements DeclarationOfPerformanceService {
	
	@Autowired
	private DeclarationOfPerformanceDao declarationOfPerformanceDao;
	
	@Override
	public void createDoP(DeclarationOfPerformance dop) {
		dop.setCreated(new DateTime());
		declarationOfPerformanceDao.save(dop);
	}

	@Override
	public void updateDop(DeclarationOfPerformance dop) {
		declarationOfPerformanceDao.update(dop);
	}

	@Override
	public void deleteDop(DeclarationOfPerformance dop) {
		declarationOfPerformanceDao.remove(dop);
	}

	@Override
	@Transactional(readOnly = true)
	public DeclarationOfPerformance getDopById(Long id) {
		return declarationOfPerformanceDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public DeclarationOfPerformance getDoPByCode(String code) {
		return declarationOfPerformanceDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DeclarationOfPerformance> getAll() {
		return declarationOfPerformanceDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public DeclarationOfPerformance getByToken(String token) {
		return declarationOfPerformanceDao.getByToken(token);
	}

}
