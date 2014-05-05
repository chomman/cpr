package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.PortalProduct;



public interface PortalProductService {
	
	List<PortalProduct> getAllNotDeleted(boolean publishedOnly);
	
	void create(PortalProduct portalService);
	
	void update(PortalProduct portalService);
	
	void delete(PortalProduct portalService);
	
	List<PortalProduct> getAll();
	
	PortalProduct getById(Long id);
	
	void createOrUpdate(PortalProduct portalService);
	
	List<PortalProduct> getAllOnlinePublications(boolean publishedOnly);
	
	List<PortalProduct> getAllRegistrations(final boolean publishedOnly);
	
}
