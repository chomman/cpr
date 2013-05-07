package sk.peterjurkovic.cpr.services.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.ExceptionLogDao;
import sk.peterjurkovic.cpr.entities.ExceptionLog;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.ExceptionLogService;
import sk.peterjurkovic.cpr.utils.ParseUtils;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("exceptionLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class ExceptionLogServiceImpl implements ExceptionLogService {
	
	
	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	@Override
	public void create(ExceptionLog exceptionLog) {
		exceptionLogDao.save(exceptionLog);
	}
	
	@Override
	public void logException(HttpServletRequest request, Exception exception){
		ExceptionLog log = new ExceptionLog();
		log.setUrl(request.getRequestURL()+ "?" +request.getQueryString());
		log.setStackTrace(ExceptionUtils.getStackTrace(exception));
		log.setMessage(exception.getMessage());
		log.setMehtod(request.getMethod());
		log.setCreated(new DateTime());
		log.setReferer(request.getHeader("referer"));
		log.setType(exception.getClass().getName());
		User user = UserUtils.getLoggedUser();
		if(user != null){
			log.setUser(user);
		}
		
		create(log);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ExceptionLog> getExceptionLogPage(int pageNumber, Map<String, Object> criteria) {
		return exceptionLogDao.getExceptionLogPage(pageNumber, validateCriteria(criteria));
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCountOfLogs(Map<String, Object> criteria) {
		return exceptionLogDao.getCountOfLogs(validateCriteria(criteria));
	}
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("orderBy", ParseUtils.parseIntFromStringObject(criteria.get("orderBy")));
			criteria.put("createdFrom", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdFrom")));
			criteria.put("createdTo", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdTo")));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public ExceptionLog getExceptionLogById(Long id) {
		return exceptionLogDao.getByID(id);
	}

}
