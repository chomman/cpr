package sk.peterjurkovic.cpr.web.controllers.admin.portal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.PortalOrder;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.PortalOrderService;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;

@Controller
public class PortalOrderController extends SupportAdminController {

	private final static String EDIT_MAPPING_URL = "/admin/portal/order/{orderId}";
	private final static String LIST_MAPPING_URL = "/admin/portal/orders";
	
	@Autowired
	private PortalOrderService portalOrderService;
	
	public PortalOrderController(){
		setTableItemsView("portal/order-list");
		setEditFormView("portal/order-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleOrderList(){
		
		
		return getTableItemsView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleOrderEdit(@PathVariable Long orderId, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		PortalOrder portalOrder = new PortalOrder();
		if(orderId != 0){
			portalOrder = getOrder(orderId);
		}
		prepareModel(map, portalOrder);
		return getEditFormView();
	}
	
	
	private void prepareModel(ModelMap map, PortalOrder portalOrder){
		map.addAttribute("portalOrder", portalOrder);
	}
	
	
	private PortalOrder getOrder(Long id) throws ItemNotFoundException{
		PortalOrder order = portalOrderService.getById(id);
		if(order == null){
			throw new ItemNotFoundException("Obejdnavka ID: " + id + " se v systému nenachází");
		}
		return order;
	}
	
}
