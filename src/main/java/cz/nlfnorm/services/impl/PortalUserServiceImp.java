package cz.nlfnorm.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.nlfnorm.constants.Filter;
import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.ParseUtils;
import cz.nlfnorm.web.json.SgpportalResponse;

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
	
	private Logger logger = Logger.getLogger(getClass());
	
	
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
	
	@Override
	public void sendRequest(){
		   RestTemplate rt = new RestTemplate();
           rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
           rt.getMessageConverters().add(new StringHttpMessageConverter());
           String uri = "http://localhost/sgp/inc/nlfnorm_api.php";
           
           Map<String, Object> args = new HashMap<String, Object>();
           args.put("token", "9dUEuPX5SLevyeFjfQb255q8LiHxiwCo");
           cz.nlfnorm.web.json.SgpportalRequest.User u = new cz.nlfnorm.web.json.SgpportalRequest.User();
            u.setUserId(5000l);
            u.setPass("123456");
            u.setLogin("test");
            args.put("user", u);
            SgpportalResponse r =  rt.postForObject(uri, HttpMethod.POST, SgpportalResponse.class, args);
           logger.info(r);
	}

	
}
