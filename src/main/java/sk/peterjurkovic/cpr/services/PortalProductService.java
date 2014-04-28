package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.PortalProduct;



public interface PortalProductService {
	
	List<PortalProduct> getAllNotDeleted(boolean publishedOnly);
	
	void create(PortalProduct portalService);
	
	void update(PortalProduct portalService);
	
	void delete(PortalProduct portalService);
	
	List<PortalProduct> getAll();
	
	PortalProduct getById(Long id);
	
	void createOrUpdate(PortalProduct portalService);
	
}
