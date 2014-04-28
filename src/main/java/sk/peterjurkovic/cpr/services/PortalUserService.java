package sk.peterjurkovic.cpr.services;

import javax.servlet.http.HttpServletRequest;

import sk.peterjurkovic.cpr.entities.User;


public interface PortalUserService {
	
	User createNewUser(User user);
	
	void createPortalOrder(User user, HttpServletRequest request);
	
}
