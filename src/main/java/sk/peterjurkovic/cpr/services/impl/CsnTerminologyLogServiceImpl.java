package sk.peterjurkovic.cpr.services.impl;

import java.util.Map;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.CsnTerminologyLogDao;
import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.CsnTerminologyLog;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.CsnTerminologyLogService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("csnTerminologyLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class CsnTerminologyLogServiceImpl implements CsnTerminologyLogService {
	
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private CsnTerminologyLogDao csnTerminologyLogDao;
	
	@Override
	public void create(CsnTerminologyLog log) {
		csnTerminologyLogDao.save(log);
	}
	@Override
	public void delete(CsnTerminologyLog log) {
		csnTerminologyLogDao.remove(log);
	}
	@Override
	public void update(CsnTerminologyLog log) {
		csnTerminologyLogDao.update(log);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CsnTerminologyLog getById(Long id) {
		return csnTerminologyLogDao.getByID(id);
	}
	
	@Override
	public void createWithUser(CsnTerminologyLog log) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		if(user != null){
			log.setCreatedBy(user);
		}
		log.setCreated(new LocalDateTime());
		create(log);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public PageDto getLogPage(int currentPage, Map<String, Object> criteria) {
		return csnTerminologyLogDao.getLogPage(currentPage, validateCriteria(criteria));
	}
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("createdFrom", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdFrom")));
			criteria.put("createdTo", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdTo")));
			criteria.put("success", ParseUtils.parseStringToBoolean(criteria.get("success")));
		}
		return criteria;
	}
}

