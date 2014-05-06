package cz.nlfnorm.services.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.dao.UserDao;
import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserOnlinePublication;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.spring.security.MD5Crypt;
import cz.nlfnorm.web.json.dto.SgpportalRequest;
import cz.nlfnorm.web.json.dto.SgpportalResponse;
import cz.nlfnorm.web.json.dto.SgpportalUser;


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
	
	@Value("#{config['sgpportal.token']}")
	private String authToken; 
	
	@Value("#{config['sgpportal.url']}")
	private String apiUrl;
	
	
	@Override
	public User createNewUser(User user) {
		Validate.notNull(user);
		user.setSgpPassword(cryptedPassowrd(user.getPassword()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Authority portalUser = authorityDao.getByCode(Authority.ROLE_PORTAL_USER);
		Validate.notNull(portalUser);
		user.getAuthoritySet().add(portalUser);
		user.setChanged(new LocalDateTime());
		user.setCreated(new LocalDateTime());
		userService.saveUser(user);
		return user;
	}
	
	private String cryptedPassowrd(String plainPass){
		return MD5Crypt.crypt(plainPass);
	}

			
	@Override
	public void syncUser(User user){
		Validate.notNull(user);
		SgpportalRequest req = new SgpportalRequest();
		req.setUser(new SgpportalUser(user));
		syncUserOnlinePublicaions(user, req);
	}
	
	@Override
	public void syncUserOnlinePublicaions(final User user){
		syncUserOnlinePublicaions(user, new SgpportalRequest());
	}
	
	
	private void syncUserOnlinePublicaions(final User user, SgpportalRequest request){
		Validate.notNull(user);
		List<UserOnlinePublication> publicationList = user.getAllActiveUserOnlinePublications();
		if(CollectionUtils.isNotEmpty(publicationList)){
			request.addAllUserOnlinePublications(publicationList);
		}
		prepareRequest(request);
	}
	
	private String toJsonString(final SgpportalRequest object) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	private void prepareRequest(SgpportalRequest requesBody){
	   MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
	   try {
			data.add("data", toJsonString(requesBody));
	   }catch (JsonProcessingException e) {
			logger.error(e);
	   }
	   data.add("token", authToken);
	   sendRequestInNewThread(data);
	}
	
	private void sendRequestInNewThread(final MultiValueMap<String, String> data){
		Thread thread = new Thread() {
			 public void run() {
				  RestTemplate restTemplate = new RestTemplate();
				  SgpportalResponse response =  restTemplate.postForObject(apiUrl, data , SgpportalResponse.class);
				  if(response.getStatus() == SgpportalResponse.STATUS_ERROR){
					  logger.error(response.getMessage());
				  }else{
					  logger.info(response);
				  }
			 }
		 };
		 thread.run(); 
	}

	
}
