package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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

import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;

/**
 * QUASAR Component
 * 
 * Controller fo Partner requests
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
@Controller
public class PartnerController extends QuasarSupportController {
	
	private final static int TAB = 4; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/partners";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/partner/{partnerId}";

	@Autowired
	private PartnerService partnerService;
	@Autowired
	private UserService userService;

	public PartnerController(){
		setTableItemsView("partner-list");
		setEditFormView("partner-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(User.class, new IdentifiableByLongPropertyEditor<User>(userService));
    }
	
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handlePartnerListPage(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("partners", partnerService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String handlePartnerEditPage(ModelMap modelMap, HttpServletRequest request, @PathVariable long partnerId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		Partner form = new Partner();
		if(partnerId != 0){
			form = partnerService.getById(partnerId);
			validateNotNull(form, "Can not display edit form. Partner [ID"+partnerId+"] was not found.");
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String handleSubmit(ModelMap modelMap, @Valid @ModelAttribute("partner") Partner form, BindingResult result) throws ItemNotFoundException{
		
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final Long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{codeId}", id+""));
	}
	
	
	private Long createOrUpdate(final Partner form) throws ItemNotFoundException{
		Partner partner = null;
		if(form.getId() == null){
			partner = new Partner();
		}else{
			partner = partnerService.getById(form.getId());
			validateNotNull(partner, "Partner could not be created. Was not found.");
		}
		partner.setManager(form.getManager());
		partner.setEnabled(form.isEnabled());
		partner.setName(StringUtils.trim(form.getName()));
		partnerService.createOrUpdate(partner);
		return partner.getId();
	}
	
	
	private void prepareModel(ModelMap map, Partner form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		map.addAttribute("partner", form);
		appendModel(map, model);
	}
	
	
}
