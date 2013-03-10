package sk.peterjurkovic.cpr.services.impl;

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

}
