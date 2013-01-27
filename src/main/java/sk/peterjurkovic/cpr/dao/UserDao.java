package sk.peterjurkovic.cpr.dao;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;



public interface UserDao extends BaseDao<User, Long>{

	User getUserByUsername(String username);
	
	List<User> getUsersByRole(String roleName);
	
	List<Authority> getAllAuthorities();
	
//	User getUserByPasswordToken(String token);
	
	List<User> autocomplateSearch(String query);
	
	List<User> getUserPage(int pageNumber, Map<String, Object> criteria);
	
	Long getCountOfUsers(Map<String, Object> criteria);
	
	boolean isUserNameUniqe(Long id, String userName);
}
