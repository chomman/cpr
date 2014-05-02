package sk.peterjurkovic.cpr.services;

import java.util.List;
import java.util.Map;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.PortalOrder;

public interface PortalOrderService {
	
	void create(PortalOrder pordalOrder);
	
	void update(PortalOrder pordalOrder);
	
	void delete(PortalOrder pordalOrder);
	
	void mergeAndSetChange(PortalOrder order);
	
	PortalOrder getById(Long id);
	
	List<PortalOrder> getAll();
	
	PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria);
	
	void updateAndSetChanged(PortalOrder order, boolean sendEmail);
	
	void activateProducts(PortalOrder order);
	
}
