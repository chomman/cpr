package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import sk.peterjurkovic.cpr.dao.BasicRequirementDao;
import sk.peterjurkovic.cpr.entities.BasicRequirement;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.BasicRequirementService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

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
	public BasicRequirement getBasicRequirementById(Long id) {
		return basicRequirementDao.getByID(id);
	}

	@Override
	public List<BasicRequirement> getAllBasicRequirements() {
		return basicRequirementDao.getAll();
	}

	@Override
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

}
