package sk.peterjurkovic.cpr.dao;

import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.PortalOrder;

public interface PortalOrderDao extends BaseDao<PortalOrder, Long> {

	PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria);
	
}
