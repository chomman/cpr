package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.PortalService;



public interface PortalServiceService {
	
	List<PortalService> getAllNotDeleted(boolean publishedOnly);
	
	void create(PortalService portalService);
	
	void update(PortalService portalService);
	
	void delete(PortalService portalService);
	
	List<PortalService> getAll();
	
	PortalService getById(Long id);
	
	void createOrUpdate(PortalService portalService);
	
}
