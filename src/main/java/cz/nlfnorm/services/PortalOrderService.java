package cz.nlfnorm.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.support.RequestContext;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.entities.PortalOrder;

public interface PortalOrderService {
	
	void create(PortalOrder pordalOrder);
	
	void update(PortalOrder pordalOrder);
	
	void delete(PortalOrder pordalOrder);
	
	void mergeAndSetChange(PortalOrder order);
	
	PortalOrder getById(Long id);
	
	PortalOrder getByCode(String code);
	
	List<PortalOrder> getAll();
	
	PageDto getPortalOrderPage(int currentPage, Map<String, Object> criteria);
	
	void updateAndSetChanged(PortalOrder order);
	
	void activateProducts(PortalOrder order);

	List<PortalOrder> getUserOrders(Long userId);
	
	void sendOrderCreateEmail(PortalOrder order, RequestContext requestContext);
	
	void sendOrderActivationEmail(PortalOrder order);
	
	void sendOrderCancelationEmail(PortalOrder order);
	
	StringBuilder getForamtedOrderItems(PortalOrder order);
	
	String getFileNameFor(int type, PortalOrder portalOrder);
	
	Map<String, Object> prepareInvoiceModel(PortalOrder order, BasicSettings settings, int type, RequestContext context);
	
}
