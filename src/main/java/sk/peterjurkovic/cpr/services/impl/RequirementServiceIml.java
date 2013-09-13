package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.RequirementDao;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.RequirementService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("requirementService")
@Transactional(propagation = Propagation.REQUIRED)
public class RequirementServiceIml implements RequirementService {
	
	@Autowired
	private RequirementDao requirementDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createRequirement(Requirement requirement) {
		requirementDao.save(requirement);	
	}

	@Override
	public void updateRequirement(Requirement requirement) {
		requirementDao.update(requirement);
	}

	@Override
	public void deleteRequirement(Requirement requirement) {
		requirementDao.remove(requirement);
	}

	@Override
	@Transactional(readOnly = true)
	public Requirement getRequirementById(Long id) {
		return requirementDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Requirement getRequirementByCode(String code) {
		return requirementDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Requirement> getAllRequirements() {
		return requirementDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Requirement> getRequirementsByCountryAndStandard(
			Country country, Standard standard) {
		return requirementDao.getRequirementsByCountryAndStandard(country, standard);
	}

	@Override
	public void saveOrUpdateRequirement(Requirement requirement) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(requirement.getId() == null){
			requirement.setCreatedBy(user);
			requirement.setCreated(new LocalDateTime());
			requirementDao.save(requirement);
		}else{
			requirement.setChangedBy(user);
			requirement.setChanged(new LocalDateTime());
			requirementDao.update(requirement);
		}
		
	}

}
