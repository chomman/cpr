package cz.nlfnorm.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.UserLogDao;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserLog;
import cz.nlfnorm.services.UserLogService;
import cz.nlfnorm.utils.ParseUtils;


@Service("userLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserLogServiceImpl implements UserLogService{
	
	@Autowired
	private UserLogDao userLogDao;
	
	@Override
	public void saveSuccessLogin(User user, String ipAddress, long timestamp, String sessionId) {
		if(user != null && user.isPortalUser()){
			UserLog logItem = new UserLog();
			logItem.setIpAddress(ipAddress);
			logItem.setLoginSuccess(Boolean.TRUE);
			logItem.setSessionId(sessionId);
			logItem.setUser(user);
			logItem.setLoginDateAndTime(new DateTime(timestamp));
			userLogDao.save(logItem);
		}
		
	}

	
	@Override
	public void saveFailureLogin(String username, String ipAddress, long timestamp) {
			UserLog logItem = new UserLog();
	        logItem.setUserName(username);
	        logItem.setIpAddress(ipAddress);
	        logItem.setLoginSuccess(false);
	        logItem.setLoginDateAndTime(new DateTime(timestamp));
	        userLogDao.save(logItem);
	}

	
	
	@Override
	public void saveLogOut(User user, long timestamp, String sessionId) {
		if(StringUtils.isNotBlank(sessionId) && user != null && user.isPortalUser()){
			UserLog userLog = userLogDao.getBySessionId(sessionId);
	        if (userLog != null) {
	            userLog.setLogoutDateAndTime(new DateTime(timestamp));
	            userLogDao.merge(userLog);
	        }
		}
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<UserLog> getLogPage(int pageNumber, Map<String, Object> criteria) {
		return userLogDao.getLogPage(pageNumber, validateCriteria(criteria));
	}

	
	
	@Override
	@Transactional(readOnly = true)
	public Long getCountOfLogs(Map<String, Object> criteria) {
		return userLogDao.getCountOfLogs(validateCriteria(criteria));
	}
	
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put("orderBy", ParseUtils.parseIntFromStringObject(criteria.get("orderBy")));
			criteria.put("createdFrom", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdFrom")));
			criteria.put("createdTo", ParseUtils.parseDateTimeFromStringObject(criteria.get("createdTo")));
		}
		return criteria;
	}
}
