package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.PortalProduct;

public interface PortalProductDao extends BaseDao<PortalProduct, Long>{

	List<PortalProduct> getAllNotDeleted(boolean publishedOnly);
	
	List<PortalProduct> getAllOnlinePublications(boolean publishedOnly);
	
	List<PortalProduct> getAllRegistrations(final boolean publishedOnly);
	
}
