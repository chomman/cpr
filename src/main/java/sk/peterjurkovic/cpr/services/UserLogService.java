package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.entities.UserLog;


public interface UserLogService {
	
	
		/**
		 * Uloží úspešné prihlasenie užívateľa, okrem role (ROLE_USER)
		 * 
		 * @param User prihlásený uživateľ
		 * @param ipAddress ID adresa
		 * @param timestamp čas prihlásenia vo formáte Unix timestamp
		 * @param sessionId 
		 */
	 	void saveSuccessLogin(User user, String ipAddress, long timestamp, String sessionId);

	 	
	 	/**
	 	 * Uloží neúspešné prihlásenie učžívateľa, okrem role (ROLE_USER)
	 	 * 
	 	 * @param username prihlasovacie meno
		 * @param ipAddress ID adresa
		 * @param timestamp čas prihlásenia vo formáte Unix timestamp
	 	 */
	 	void saveFailureLogin(String username, String ipAddress, long timestamp);

	 	
	 	
	    /**
	     * Uloží odhlásenie užívateľa, okrem role (ROLE_USER)
	     * 
	 	 * @param username prihlasovacie meno
		 * @param ipAddress ID adresa
		 * @param timestamp čas prihlásenia vo formáte Unix timestamp
		 */
	 	void saveLogOut(User user, long timestamp, String sessionId);
	 	
	 	
	 	List<UserLog> getLogPage(int pageNumber, Map<String, Object> criteria);
	 	
	 	
	 	Long getCountOfLogs(Map<String, Object> criteria);
}
