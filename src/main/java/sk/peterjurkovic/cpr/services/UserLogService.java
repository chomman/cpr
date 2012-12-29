package sk.peterjurkovic.cpr.services;

import sk.peterjurkovic.cpr.entities.User;


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
}
