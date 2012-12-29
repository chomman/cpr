package sk.peterjurkovic.cpr.spring.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = null;
		
		try {
			user = userDao.getUserByUsername(username);
	        if (user == null) {
	        	logger.debug("User not found, throwing ex...");
	            throw new UsernameNotFoundException("User not found");
	        }
	        
	        logger.debug("User: " + user.toString());
	        
	        if (user.getAuthorities().isEmpty()) {
	        	logger.debug("Empty user authorities.. throwing ex..");
	            throw new UsernameNotFoundException("User has no GrantedAuthority");
	        }
		} catch (Exception e) {
		   logger.error("Error in retrieving user: " + e.getMessage());
		   throw new UsernameNotFoundException("Error in retrieving user");
		 }   
        return (UserDetails)user;
	}

	
}
