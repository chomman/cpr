package sk.peterjurkovic.cpr.services.impl;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.StandardDao;
import sk.peterjurkovic.cpr.entities.Standard;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.StandardService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("standardService")
@Transactional(propagation = Propagation.REQUIRED)
public class StandardServiceImpl implements StandardService {
	
	@Autowired
	private StandardDao standardDao;
	@Autowired
	private UserService userService;
	
	@Override
	public void createStandard(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public void updateStandard(Standard standard) {
		standardDao.update(standard);
	}

	@Override
	public void deleteStandard(Standard standard) {
		standardDao.remove(standard);
	}

	@Override
	@Transactional(readOnly =  true )
	public Standard getStandardById(Long id) {
		return standardDao.getByID(id);
	}

	@Override
	@Transactional(readOnly =  true )
	public Standard getStandardByCode(String code) {
		return standardDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getAllStandards() {
		return standardDao.getAll();
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Standard> getStandardPage(int pageNumber,
			Map<String, Object> criteria) {
		
		Long standardGroupId = ParseUtils.parseLongFromStringObject("standardGroup");
		int orderById = ParseUtils.parseIntFromStringObject(criteria.get("orderBy"));
		DateTime startValidity = ParseUtils.parseDateTimeFromStringObject(criteria.get("startValidity"));
		DateTime stopValidity =  ParseUtils.parseDateTimeFromStringObject(criteria.get("stopValidity"));		
		String query = null;
		if(criteria.get("query") != null){
			query = (String) criteria.get("query");
		}
		return standardDao.getStandardPage(pageNumber, standardGroupId, orderById, query, startValidity, stopValidity);
	}

	@Override
	@Transactional(readOnly =  true )
	public Long getCountOfStandards(Map<String, Object> criteria) {
		Long standardGroupId = ParseUtils.parseLongFromStringObject("standardGroup");
		int orderById = ParseUtils.parseIntFromStringObject(criteria.get("orderBy"));
		DateTime startValidity = ParseUtils.parseDateTimeFromStringObject(criteria.get("startValidity"));
		DateTime stopValidity =  ParseUtils.parseDateTimeFromStringObject(criteria.get("stopValidity"));		
		String query = null;
		if(criteria.get("query") != null){
			query = (String) criteria.get("query");
		}
		return standardDao.getCountOfSdandards(standardGroupId, orderById, query, startValidity, stopValidity);
	}

	@Override
	@Transactional(readOnly =  true )
	public boolean isStandardIdUnique(String standardId, Long id) {
		return standardDao.isStandardIdUnique(standardId, id);
	}

	@Override
	public void saveOrUpdate(Standard standard) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		
		if(standard.getId() == null){
			standard.setCreatedBy(user);
			standard.setCreated(new DateTime());
			standardDao.save(standard);
			standardDao.flush();
		}else{
			standard.setChangedBy(user);
			standard.setChanged(new DateTime());
			standardDao.update(standard);
		}
		
	}

	@Override
	public void clearStandardTags(Standard standard) {
		standardDao.clearStandardTags(standard);
	}

	@Override
	@Transactional(readOnly =  true )
	public List<Standard> autocomplateSearch(String query) {
		return standardDao.autocomplateSearch(query);
	}

}
