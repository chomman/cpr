package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.BasicRequirementDao;
import sk.peterjurkovic.cpr.entities.BasicRequirement;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.CodeUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("basicRequirementService")
@Transactional(propagation = Propagation.REQUIRED)
public class BasicRequirementServiceImpl implements BasicRequirementService {
	
	@Autowired
	private BasicRequirementDao basicRequirementDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createBasicRequirement(BasicRequirement basicRequirement) {
		basicRequirementDao.save(basicRequirement);
	}

	@Override
	public void updateBasicRequirement(BasicRequirement basicRequirement) {
		basicRequirementDao.update(basicRequirement);
	}

	@Override
	public void deleteBasicRequirement(BasicRequirement basicRequirement) {
		basicRequirementDao.remove(basicRequirement);
	}

	@Override
	@Transactional(readOnly = true)
	public BasicRequirement getBasicRequirementById(Long id) {
		return basicRequirementDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BasicRequirement> getAllBasicRequirements() {
		return basicRequirementDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public BasicRequirement getBasicRequirementByCode(String code) {
		return basicRequirementDao.getByCode(code);
	}

	@Override
	public void saveOrUpdateBasicRequirement(BasicRequirement basicRequirement) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(basicRequirement.getId() == null){
			basicRequirement.setCreatedBy(user);
			basicRequirement.setCreated(new DateTime());
			basicRequirementDao.save(basicRequirement);
		}else{
			basicRequirement.setChangedBy(user);
			basicRequirement.setChanged(new DateTime());
			basicRequirementDao.update(basicRequirement);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isBasicRequirementNameUniqe(String code, Long id) {
		return basicRequirementDao.isNameUniqe(CodeUtils.toSeoUrl(code), id);
	}

}
