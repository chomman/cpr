package cz.nlfnorm.web.controllers.admin.portal;

import java.util.HashMap;
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

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.enums.OrderStatus;
import cz.nlfnorm.enums.PortalCountry;
import cz.nlfnorm.enums.PortalOrderOrder;
import cz.nlfnorm.enums.PortalOrderSource;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.PortalOrderService;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;
import cz.nlfnorm.web.editors.PortalProductPropertyEditor;

@Controller
public class PortalOrderController extends AdminSupportController {

	private final static String EDIT_MAPPING_URL = 		"/admin/portal/order/{orderId}";
	private final static String LIST_MAPPING_URL = 		"/admin/portal/orders";
	private final static String DELETE_MAPPING_URL = 	"/admin/portal/order/delete/{orderId}";
	
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
			model.put("paginationLinks", getPaginationItems(request,params, page.getCount(), LIST_MAPPING_URL));
			model.put("portalOrders", page.getItems() );
		}
		model.put("params", params);
		model.put("orders", PortalOrderOrder.getAll());
		model.put("orderStatuses", OrderStatus.getAll());
		model.put("sources", PortalOrderSource.getAll());
		model.put("tab", 1);
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleOrderEdit(@PathVariable Long orderId, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		PortalOrder portalOrder = new PortalOrder();
		if(orderId != 0){
			portalOrder = getOrder(orderId);
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(map);
		}
		prepareModel(map, portalOrder);
		return getEditFormView();
	}
	
	@RequestMapping(DELETE_MAPPING_URL)
	public String removePortalOrder(@PathVariable Long orderId) throws ItemNotFoundException{
		final PortalOrder order = getOrder(orderId);
		order.setDeleted(true);
		portalOrderService.update(order);
		return successDeleteRedirect(LIST_MAPPING_URL);
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
	
	@RequestMapping(value = EDIT_MAPPING_URL + "/delete/{itemId}", method = RequestMethod.GET)
	public String handleRemoveOrderItem(@PathVariable Long orderId, @PathVariable Long itemId) throws ItemNotFoundException{
		PortalOrder portalOrder = getOrder(orderId);
			if(portalOrder.removeOrderItem(itemId)){
				portalOrderService.mergeAndSetChange(portalOrder);
				return successUpdateRedirect(EDIT_MAPPING_URL.replace("{orderId}", orderId.toString()));
			}
		return  "redirect:"+ EDIT_MAPPING_URL.replace("{orderId}", orderId.toString());
	}
	
		
	private void update(PortalOrder form) throws ItemNotFoundException {
		PortalOrder portalOrder = portalOrderService.getById(form.getId());
		if(portalOrder == null){
			throw new ItemNotFoundException("Objednavka s ID" + form.getId() + " se nenašla");
		}
		portalOrder.merge(form);
		portalOrderService.updateAndSetChanged(portalOrder);
	}

	private void prepareModel(ModelMap map, PortalOrder portalOrder){
		map.addAttribute("portalOrder", portalOrder);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("portalCountries", PortalCountry.getAll());
		model.put("orderStatuses", OrderStatus.getAll());
		model.put("portalProducts", portalProductService.getAll() );
		model.put("order", portalOrderService.getById(portalOrder.getId()));
		model.put("tab", 1);
		map.put("model", model);
	}
	
	
	private PortalOrder getOrder(Long id) throws ItemNotFoundException{
		PortalOrder order = portalOrderService.getById(id);
		if(order == null){
			throw new ItemNotFoundException("Obejdnavka ID: " + id + " se v systému nenachází");
		}
		return order;
	}
	
	
	
}
