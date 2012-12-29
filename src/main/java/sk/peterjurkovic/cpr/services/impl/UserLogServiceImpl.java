package sk.peterjurkovic.cpr.services.impl;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.UserLogDao;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.UserLog;
import sk.peterjurkovic.cpr.services.UserLogService;


@Service("userLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserLogServiceImpl implements UserLogService{
	
	@Autowired
	private UserLogDao userLogDao;
	
	@Override
	public void saveSuccessLogin(User user, String ipAddress, long timestamp, String sessionId) {
		if(user != null && user.isAdminUser()){
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
		if(StringUtils.isNotBlank(sessionId) && user != null && user.isAdminUser()){
			UserLog userLog = userLogDao.getBySessionId(sessionId);
	        if (userLog != null) {
	            userLog.setLogoutDateAndTime(new DateTime(timestamp));
	            userLogDao.merge(userLog);
	        }
		}
	}

}
