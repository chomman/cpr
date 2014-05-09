package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.PortalOrder;

public interface PortalOrderService {
	
	void create(PortalOrder pordalOrder);
	
	void update(PortalOrder pordalOrder);
	
	void delete(PortalOrder pordalOrder);
	
	void mergeAndSetChange(PortalOrder order);
	
	PortalOrder getById(Long id);
	
	List<PortalOrder> getAll();
	
	PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria);
	
	void updateAndSetChanged(PortalOrder order);
	
	void activateProducts(PortalOrder order);

	List<PortalOrder> getUserOrders(Long userId);
	
	void sendOrderCreateEmail(PortalOrder order);
	
	void sendOrderActivationEmail(PortalOrder order);
	
	void sendOrderCancelationEmail(PortalOrder order);
	
	StringBuilder getForamtedOrderItems(PortalOrder order);
}
