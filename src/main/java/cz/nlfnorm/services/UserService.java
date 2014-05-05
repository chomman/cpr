package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;



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
	
	PageDto getUserPage(int pageNumber, Map<String, Object> criteria);
		
	void createOrUpdateUser(User user);
	
	List<User> autocomplateSearch(String query);
	
}
