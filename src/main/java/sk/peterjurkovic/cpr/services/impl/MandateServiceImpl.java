package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.MandateDao;
import sk.peterjurkovic.cpr.entities.Mandate;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.MandateService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("mandateService")
@Transactional(propagation = Propagation.REQUIRED)
public class MandateServiceImpl implements MandateService {
	
	@Autowired
	private MandateDao mandateDao;
	@Autowired
	private UserService userService;
	
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

	@Override
	@Transactional(readOnly = true)
	public List<Mandate> getMandatePage(int pageNumber) {
		return mandateDao.getMandatePage(pageNumber);
	}

	@Override
	public void saveOrUpdateMandate(Mandate mandate) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(mandate.getId() == null){
			mandate.setCreatedBy(user);
			mandate.setCreated(new DateTime());
			mandateDao.save(mandate);
		}else{
			mandate.setChangedBy(user);
			mandate.setChanged(new DateTime());
			mandateDao.update(mandate);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCountOfMandates() {
		return mandateDao.getCountOfMandates();
	}

}
