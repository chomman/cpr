package sk.peterjurkovic.cpr.web.controllers.admin.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;

@Controller
public class PortalOrderController extends SupportAdminController {

	
	public PortalOrderController(){
		setTableItemsView("portal/order-list");
		setEditFormView("portal/order-edit");
	}
	
	@RequestMapping("/admin/portal/orders")
	public String handleOrderList(){
		
		
		return getTableItemsView();
	}
	
}
