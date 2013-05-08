package sk.peterjurkovic.cpr.spring.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.User;

/**
 * Implementacia autentizacneho managera springu
 * 
 * @author peto
 *
 */
@Transactional(readOnly = true)
public class AuthenticationManagerImpl implements AuthenticationManager {

	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Overi, ci su autentizacne udaje platne.
	 * 
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
				 
		 User user = null;
		 
		 try{
			 logger.debug("Searching user: "+authentication.getName());
			 user = userDao.getUserByUsername(authentication.getName());
			 
			 if(user == null){
				 throw new BadCredentialsException("User does not exists!");
			 }
		 }catch(Exception ex){
			 throw new BadCredentialsException("User does not exists!");
		 }
		 
		 
		 if (  passwordEncoder.isPasswordValid(user.getPassword(), (String) authentication.getCredentials(), null) == false ) {
			   throw new BadCredentialsException("Wrong password!");
		 }
		 
		// Here's the main logic of this custom authentication manager
		  // Username and password must be the same to authenticate
		  if (authentication.getName().equals(authentication.getCredentials()) == true) {
		   throw new BadCredentialsException("Entered username and password are the same!");
		    
		  } else {
		   return new UsernamePasswordAuthenticationToken(
				   authentication.getName(),
				   authentication.getCredentials(),
				   user.getAuthorities());
		  }
	}

}
