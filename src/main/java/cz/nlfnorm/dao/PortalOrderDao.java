package cz.nlfnorm.dao;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.PortalOrder;

public interface PortalOrderDao extends BaseDao<PortalOrder, Long> {

	PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria);
	
	List<PortalOrder> getUserOrders(Long userId);
}
