package cz.nlfnorm.services;

import cz.nlfnorm.entities.User;


public interface PortalUserService {
	
	User createNewUser(User user);
				
	void syncUserOnlinePublicaions(User user);
	
	void syncUser(User user);
}
