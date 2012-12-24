package sk.peterjurkovic.cpr.services;

import java.util.List;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;



public interface UserService {

	
	Authority getAuthorityByCode(String code);
	
	List<User> getUsers();
	
	User getUserByUsername(String username);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	User mergeUser(User user);
	
	User getById(Long id);
	
	void removeUser(User user);
	
	void disableUser(User user);
	
	void enableUser(User user);
	
	Authority getAuthorityByName(String code);
	
	boolean existsUser(String username);
	
	List<Authority> getAuthorities();
}
