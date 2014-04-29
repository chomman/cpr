package sk.peterjurkovic.cpr.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.dto.PageDto;
import sk.peterjurkovic.cpr.entities.PortalOrder;
import sk.peterjurkovic.cpr.entities.PortalProduct;
import sk.peterjurkovic.cpr.enums.OrderStatus;
import sk.peterjurkovic.cpr.enums.PortalOrderOrder;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.PortalOrderService;
import sk.peterjurkovic.cpr.services.PortalProductService;
import sk.peterjurkovic.cpr.utils.RequestUtils;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;
import sk.peterjurkovic.cpr.web.editors.PortalProductPropertyEditor;
import sk.peterjurkovic.cpr.web.pagination.PageLink;
import sk.peterjurkovic.cpr.web.pagination.PaginationLinker;

@Controller
public class PortalOrderController extends SupportAdminController {

	private final static String EDIT_MAPPING_URL = "/admin/portal/order/{orderId}";
	private final static String LIST_MAPPING_URL = "/admin/portal/orders";
	
	@Autowired
	private PortalOrderService portalOrderService;
	@Autowired
	private PortalProductService portalProductService;
	@Autowired
	private PortalProductPropertyEditor portalProductPropertyEditor;
	
	public PortalOrderController(){
		setTableItemsView("portal/order-list");
		setEditFormView("portal/order-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(PortalProduct.class, portalProductPropertyEditor);
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleOrderList(HttpServletRequest request,  ModelMap modelMap){
		Map<String, Object> model = new HashMap<String, Object>();
		int currentPage = RequestUtils.getPageNumber(request);
		Map<String, Object> params = RequestUtils.getRequestParameterMap(request);
		PageDto page = portalOrderService.getPortalOrderPage(currentPage, params);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request,params, currentPage, page.getCount()));
			model.put("portalOrders", page.getItems() );
		}
		model.put("params", params);
		model.put("orders", PortalOrderOrder.getAll());
		model.put("orderStatuses", OrderStatus.getAll());
		modelMap.put("model", model);
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
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processOrderEdit(ModelMap map, @Valid @ModelAttribute("portalOrder") PortalOrder portalOrder, BindingResult result ) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(map, portalOrder);
			return getEditFormView();
		}
		update(portalOrder);
		map.put(SUCCESS_CREATE_PARAM, true);
		prepareModel(map, portalOrder);
		return getEditFormView();
	}
	
	
	private void update(PortalOrder form) throws ItemNotFoundException {
		PortalOrder portalOrder = portalOrderService.getById(form.getId());
		if(portalOrder == null){
			throw new ItemNotFoundException("Objednavka s ID" + form.getId() + " se nenašla");
		}
		boolean sendEmail = portalOrder.merge(form);
		portalOrderService.updateAndSetChanged(portalOrder, sendEmail);
	}

	private void prepareModel(ModelMap map, PortalOrder portalOrder){
		map.addAttribute("portalOrder", portalOrder);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orderStatuses", OrderStatus.getAll());
		model.put("portalProducts", portalProductService.getAll() );
		model.put("order", portalOrderService.getById(portalOrder.getId()));
		map.put("model", model);
	}
	
	
	private PortalOrder getOrder(Long id) throws ItemNotFoundException{
		PortalOrder order = portalOrderService.getById(id);
		if(order == null){
			throw new ItemNotFoundException("Obejdnavka ID: " + id + " se v systému nenachází");
		}
		return order;
	}
	
	private  List<PageLink> getPaginationItems(HttpServletRequest request, Map<String, Object> params, int currentPage, int count){
		PaginationLinker paginger = new PaginationLinker(request, params);
		paginger.setUrl("/admin/orders");
		paginger.setCurrentPage(currentPage);
		paginger.setRowCount(count);
		return paginger.getPageLinks(); 
	}
	
}
