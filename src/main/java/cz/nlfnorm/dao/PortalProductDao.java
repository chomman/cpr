package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.PortalProduct;

public interface PortalProductDao extends BaseDao<PortalProduct, Long>{

	List<PortalProduct> getAllNotDeleted(boolean publishedOnly);
	
	List<PortalProduct> getAllOnlinePublications(boolean publishedOnly);
	
	List<PortalProduct> getAllRegistrations(final boolean publishedOnly);
	
}
