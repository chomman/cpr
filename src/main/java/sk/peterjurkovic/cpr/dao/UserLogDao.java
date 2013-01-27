package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.UserLog;


public interface UserLogDao extends BaseDao<UserLog, Long>{
	
	UserLog getBySessionId(String sessionId);
	
	List<UserLog> getLogPage(int pageNumber, Map<String, Object> criteria);
	
	Long getCountOfLogs(Map<String, Object> criteria);
	
}
