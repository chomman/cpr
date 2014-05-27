package cz.nlfnorm.services.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.web.forms.portal.ResetPassowrdForm;
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
	private AuthorityDao authorityDao;
	@Autowired
	private ExceptionLogService exceptionLogService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Value("#{config['sgpportal.token']}")
	private String authToken; 
	
	@Value("#{config['sgpportal.url']}")
	private String apiUrl;
	
	
	/**
	 * Create new portal user. Set hashed password, setup privileges {@link Authority.ROLE_PORTAL_USER}
	 * @return persisted user
	 */
	@Override
	public User createNewUser(User user) {
		Validate.notNull(user);
		userService.setUserPassword(user, user.getPassword());
		Authority portalUser = authorityDao.getByCode(Authority.ROLE_PORTAL_USER);
		Validate.notNull(portalUser);
		user.getAuthoritySet().add(portalUser);
		userService.createOrUpdateUser(user);
		return user;
	}
			
	@Override
	public void syncUser(User user){
		Validate.notNull(user);
		SgpportalRequest req = new SgpportalRequest();
		req.setUser(new SgpportalUser(user));
		req.createSqpAccessFor(user);
		syncUserOnlinePublicaions(user, req);
	}
	
	@Override
	public void syncUserOnlinePublicaions(final User user){
		syncUserOnlinePublicaions(user, new SgpportalRequest());
	}
	
	
	@Override
	public void setSynchronizationFailedFor(final Long userId){
		User user = userService.getUserById(userId);
		if(user != null && user.getUserInfo() != null){
			user.getUserInfo().setSynced(false);
			userService.createOrUpdateUser(user);
			// TODO send alert to email
		}
	}
	
	private void syncUserOnlinePublicaions(final User user, SgpportalRequest request){
		Validate.notNull(user);
		List<UserOnlinePublication> publicationList = user.getAllActiveUserOnlinePublications();
		if(CollectionUtils.isNotEmpty(publicationList)){
			request.addAllUserOnlinePublications(publicationList);
		}
		prepareRequest(request, user.getId());
	}
	
	private String toJsonString(final SgpportalRequest object) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	

	private void prepareRequest(final SgpportalRequest requesBody, final long userId){
	   MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
	   try {
			data.add("data", toJsonString(requesBody));
	   }catch (JsonProcessingException e) {
			logger.error(e);
	   }
	   data.add("token", authToken);
	   sendRequestInNewThread(data, userId);
	}
	
	
	
	private void sendRequestInNewThread(final MultiValueMap<String, String> data, final long userId){
		Thread thread = new Thread() {
			 public void run() {
				 boolean syncFailed = true; 
				 try{
					  RestTemplate restTemplate = new RestTemplate();
					  SgpportalResponse response =  restTemplate.postForObject(apiUrl, data , SgpportalResponse.class);
					  if(response.getStatus() == SgpportalResponse.STATUS_ERROR){
						  logger.warn(response.getMessage());
					  }else{
						  syncFailed = false;
						  logger.info(response);
					  }
				  }catch(Exception e){
					  exceptionLogService.logException(e);
					  logger.warn(e);
				  }
				 if(syncFailed){
					 setSynchronizationFailedFor(userId);
				 }
			 }
		 };
		 thread.run(); 
	}



	@Override
	public void changeUserPassword(final ResetPassowrdForm form) {
		User user = userService.getUserById(form.getUserId());
		Validate.notNull(user);
		userService.setUserPassword(user, form.getNewPassword());
		userService.createOrUpdateUser(user);
		syncUser(user);
	}

	
	@Override
	public void sendEmailAlerts() {
		
	}

	
}
