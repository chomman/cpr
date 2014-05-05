package cz.nlfnorm.services.impl;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ParseUtils;

@Service("portalUserService")
@Transactional(propagation = Propagation.REQUIRED)
public class PortalUserServiceImp implements PortalUserService {
	
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
	@Transactional(readOnly = true)
	public PageDto getPage(int page, Map<String, Object> criteria) {
		return null;
	}

	
	
	
	private Map<String, Object> validateCriteria(Map<String, Object> criteria){
		if(criteria.size() != 0){
			criteria.put(Filter.ORDER, ParseUtils.parseIntFromStringObject(criteria.get(Filter.ORDER)));
		}
		return criteria;
	}


	
}
