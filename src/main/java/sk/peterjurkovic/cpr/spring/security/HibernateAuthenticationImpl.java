package sk.peterjurkovic.cpr.spring.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.User;

@Transactional(readOnly = true)
public class HibernateAuthenticationImpl implements UserDetailsService {
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = null;
		
		try {
			user = userDao.getUserByUsername(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
	        	        
	        if (user.getAuthorities().isEmpty()) {
	            throw new UsernameNotFoundException("User has no GrantedAuthority");
	        }
		} catch (Exception e) {
		   throw new UsernameNotFoundException("Error in retrieving user");
		 }   
        return (UserDetails)user;
	}

	
}
