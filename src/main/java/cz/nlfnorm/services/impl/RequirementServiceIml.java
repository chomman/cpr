package cz.nlfnorm.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.RequirementDao;
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.Requirement;
import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.RequirementService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;

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
