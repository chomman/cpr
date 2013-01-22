package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;



public interface UserService {

	
	Authority getAuthorityByCode(String code);
	
	List<User> getAllUsers();
	
	User getUserByUsername(String username);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	User mergeUser(User user);
	
	User getUserById(Long id);
	
	void removeUser(User user);
	
	void disableUser(User user);
	
	void enableUser(User user);
	
	Authority getAuthorityByName(String code);
	
	boolean existsUser(String username);
	
	boolean isUserNameUniqe(Long id, String userName);
	
	public List<Authority> getAllAuthorities();
	
	List<User> getUserPage(int pageNumber, Map<String, Object> criteria);
	
	Long getCountOfUsers(Map<String, Object> criteria);
	
}
