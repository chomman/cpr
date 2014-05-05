package cz.nlfnorm.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.MandateDao;
import cz.nlfnorm.entities.Mandate;
import cz.nlfnorm.entities.StandardGroup;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.MandateService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;

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
			mandate.setCreated(new LocalDateTime());
			mandateDao.save(mandate);
		}else{
			mandate.setChangedBy(user);
			mandate.setChanged(new LocalDateTime());
			mandateDao.update(mandate);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCountOfMandates() {
		return mandateDao.getCountOfMandates();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean canBeDeleted(Mandate mandate) {
		return mandateDao.canBeDeleted(mandate);
	}

	
	
	@Override
	@Transactional(readOnly = true)
	public List<Mandate> getFiltredMandates(final Mandate mandate) {
		List<Mandate> allMandates = getAllMandates();
		allMandates.removeAll(mandate.getChanges());
		allMandates.remove(mandate);
		return allMandates;
	}

	@Override
	public List<Mandate> getFiltredMandates(StandardGroup standardGroup) {
		List<Mandate> allMandates = getAllMandates();
		for(Mandate m : standardGroup.getMandates()){
			allMandates.removeAll(m.getChanges());
			allMandates.remove(m);
		}
		return allMandates;
	}

}
