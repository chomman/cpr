package cz.nlfnorm.services;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.web.forms.portal.ChangePasswordForm;


public interface PortalUserService {
	
	User createNewUser(User user);
				
	void syncUserOnlinePublicaions(User user);
	
	void syncUser(User user);
	
	void setSynchronizationFailedFor(Long userId);
	
	void changeUserPassword(ChangePasswordForm form);
}
