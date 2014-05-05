package cz.nlfnorm.services;

import javax.servlet.http.HttpServletRequest;

import cz.nlfnorm.entities.User;


public interface PortalUserService {
	
	User createNewUser(User user);
	
	void createPortalOrder(User user, HttpServletRequest request);
	
}
