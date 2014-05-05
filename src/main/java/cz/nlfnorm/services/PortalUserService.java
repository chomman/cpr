package cz.nlfnorm.services;

import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.User;


public interface PortalUserService {
	
	User createNewUser(User user);
		
	PageDto getPage(int page, Map<String, Object> criteria);
	
}
