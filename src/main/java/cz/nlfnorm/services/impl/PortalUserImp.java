package cz.nlfnorm.services.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;

@Service("portalUserService")
@Transactional(propagation = Propagation.REQUIRED)
public class PortalUserImp implements PortalUserService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthorityDao authorityDao;
	
	
	@Override
	public User createNewUser(User user) {
		Validate.notNull(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Authority portalUser = authorityDao.getByCode(Authority.ROLE_PORTAL_USER);
		Validate.notNull(portalUser);
		user.getAuthoritySet().add(portalUser);
		user.setChanged(new LocalDateTime());
		user.setCreated(new LocalDateTime());
		userService.saveUser(user);
		return user;
	}


	@Override
	public void createPortalOrder(User user, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}



	
}
