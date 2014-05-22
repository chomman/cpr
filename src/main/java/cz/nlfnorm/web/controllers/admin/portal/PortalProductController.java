package cz.nlfnorm.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.enums.OnlinePublication;
import cz.nlfnorm.enums.PortalProductInterval;
import cz.nlfnorm.enums.PortalProductType;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.PortalProductService;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;

@Controller
public class PortalProductController extends AdminSupportController {
	
	private final static String EDIT_MAPPING_URL = "/admin/portal/product/{serviceId}";
	private final static String LIST_MAPPING_URL = "/admin/portal/products";
	
	
	@Autowired
	private PortalProductService portalProductService;
	
	
	
	public PortalProductController(){
		setTableItemsView("portal/product-list");
		setEditFormView("portal/product-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleServicesList(ModelMap modelMap, HttpServletRequest request){
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("portalProducts", portalProductService.getAllNotDeleted(false));
		model.put("tab", 3);
		modelMap.put("model", model);
		if(isDeleted(request)){
			appendSuccessDeleteParam(modelMap);
		}
		return getTableItemsView();
	}
	
	
		
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleServicesEdit(@PathVariable Long serviceId, ModelMap map, HttpServletRequest request) throws ItemNotFoundException{
		PortalProduct service =  new PortalProduct();
		if(serviceId != 0){
			service  = getPortalProduct(serviceId);
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(map);
		}
		prepareModel(map, service);
		return getEditFormView();
	}
	
	
	
	@RequestMapping("/admin/portal/product/delete/{id}")
	public String handleDelete(@PathVariable Long id) throws ItemNotFoundException{
		PortalProduct service = getPortalProduct(id);
		service.setDeleted(true);
		portalProductService.createOrUpdate(service);
		return successDeleteRedirect(LIST_MAPPING_URL); 
	}
	
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmitPortalService(@PathVariable Long serviceId, @ModelAttribute("portalProduct") @Valid PortalProduct form, BindingResult result,  ModelMap map) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(map, form);
			return getEditFormView();
		}
		Long id = createOrUpdate(form);
		return successUpdateRedirect(EDIT_MAPPING_URL.replace("{serviceId}", id.toString()));
	}
	
	
	
	private void prepareModel(ModelMap map, PortalProduct service){
		map.addAttribute("portalProduct", service);
		map.put("intervalTypes", PortalProductInterval.getAll());
		map.put("portalProductTypes", PortalProductType.getAll());
		map.put("onlinePublications", OnlinePublication.getAll());
	}
	
	
	private Long createOrUpdate(PortalProduct form) throws ItemNotFoundException{
		PortalProduct product = null;
		if(form.getId() == null){
			product = new PortalProduct();
		}else{
			product = getPortalProduct(form.getId());
		}
		product.setPortalProductType(form.getPortalProductType());
		product.setOnlinePublication(form.getOnlinePublication());
		product.setCzechName(form.getCzechName());
		product.setEnglishName(form.getEnglishName());
		product.setPriceCzk(form.getPriceCzk());
		product.setPriceEur(form.getPriceEur());
		product.setDescriptionCzech(form.getDescriptionCzech());
		product.setDescriptionEnglish(form.getDescriptionEnglish());
		product.setIntervalValue(form.getIntervalValue());
		product.setPortalProductInterval(form.getPortalProductInterval());
		product.setEnabled(form.getEnabled());
		portalProductService.createOrUpdate(product);
		return product.getId();
	}
	
	
	
	private PortalProduct getPortalProduct(final Long id) throws ItemNotFoundException{
		PortalProduct product = portalProductService.getById(id);
		if(product == null || product.getDeleted()){
			throw new ItemNotFoundException("Služba s ID: " + id + " se v systému nenachází");
		}
		return product;
	}
	
}
