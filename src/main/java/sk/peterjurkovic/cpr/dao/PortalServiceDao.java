package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.PortalService;

public interface PortalServiceDao extends BaseDao<PortalService, Long>{

	List<PortalService> getAllNotDeleted(boolean publishedOnly);
	
}
