package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;



public interface UserDao extends BaseDao<User, Long>{

	User getUserByUsername(String username);
	
	List<User> getUsersByRole(String roleName);
	
	List<User> getUsers();
	
	List<Authority> getAuthorities();
	
//	User getUserByPasswordToken(String token);
}
