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


@Transactional(readOnly = true)
public class AuthenticationManagerImpl implements AuthenticationManager {

	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		 logger.debug("Performing custom authentication");
		 
		 User user = null;
		 
		 try{
			 logger.debug("Searching user: "+authentication.getName());
			 user = userDao.getUserByUsername(authentication.getName());
			 
			 if(user == null){
				 logger.error("User does not exists!");
				 throw new BadCredentialsException("User does not exists!");
			 }
		 }catch(Exception ex){
			 logger.error("User does not exists! "  + ex.getMessage() );
			 throw new BadCredentialsException("User does not exists!");
		 }
		 
		 
		 if (  passwordEncoder.isPasswordValid(user.getPassword(), (String) authentication.getCredentials(), null) == false ) {
			   logger.error("Wrong password!");
			   throw new BadCredentialsException("Wrong password!");
		 }
		 
		// Here's the main logic of this custom authentication manager
		  // Username and password must be the same to authenticate
		  if (authentication.getName().equals(authentication.getCredentials()) == true) {
		   logger.debug("Entered username and password are the same!");
		   throw new BadCredentialsException("Entered username and password are the same!");
		    
		  } else {
		    
		   logger.debug("User details are good and ready to go");
		   return new UsernamePasswordAuthenticationToken(
				   authentication.getName(),
				   authentication.getCredentials(),
				   user.getAuthorities());
		  }
	}

}
