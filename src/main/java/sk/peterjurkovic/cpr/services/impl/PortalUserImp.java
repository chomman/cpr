package sk.peterjurkovic.cpr.services.impl;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.UserDao;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.PortalUserService;
import sk.peterjurkovic.cpr.services.UserService;

@Service("portalUserService")
@Transactional(propagation = Propagation.REQUIRED)
public class PortalUserImp implements PortalUserService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public void createNewUser(User user) {
		Validate.notNull(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.getAuthorities().add(Authority.getInstance(Authority.ROLE_PORTAL_USER));
		user.setChanged(new LocalDateTime());
		user.setCreated(new LocalDateTime());
		userService.saveUser(user);
	}
	
}
