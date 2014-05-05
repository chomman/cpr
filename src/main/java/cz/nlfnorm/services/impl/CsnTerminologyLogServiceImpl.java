package cz.nlfnorm.services.impl;

import java.util.Map;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.CsnTerminologyLogDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Csn;
import cz.nlfnorm.entities.CsnTerminologyLog;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.CsnTerminologyLogService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.utils.UserUtils;

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
	public CsnTerminologyLog getById(final Long id) {
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
	@Override
	public void removeCsnLogs(final Csn csn) {
		csnTerminologyLogDao.removeCsnLogs(csn);
	}
}

