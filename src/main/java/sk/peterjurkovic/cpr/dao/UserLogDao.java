package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.UserLog;


public interface UserLogDao extends BaseDao<UserLog, Long>{
	
	UserLog getBySessionId(String sessionId);
}
