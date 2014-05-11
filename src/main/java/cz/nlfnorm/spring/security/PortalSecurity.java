package cz.nlfnorm.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.OnlinePublication;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;


@Component("portalSecurity")
public class PortalSecurity {

	@Autowired
	private UserService userService;
	
	
	public boolean hasOnlinePublicatoin(String code){
		User user = UserUtils.getLoggedUser();
		if(user == null){
			return false;
		}
		user = userService.getUserById(user.getId());
		
		final OnlinePublication onlinePublication = OnlinePublication.getByCode(code);
		if(onlinePublication != null){
			return user.hasValidOnlinePublication(onlinePublication);
		}
		return false;
	}
	
	
	public boolean hasValidRegistration(){
		return UserUtils.isPortalAuthorized();
	}
}
