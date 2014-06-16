package cz.nlfnorm.quasar.web.controllers;

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

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.forms.AuditorForm;
import cz.nlfnorm.quasar.services.AuditorService;
import cz.nlfnorm.quasar.validators.AuditorValidator;
import cz.nlfnorm.services.UserService;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Controller
public class AuditorController extends QuasarSupportController {
	
	private final static int TAB = 3; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/auditors";
	private final static String ADD_AUDITOR_MAPPING_URL = "/admin/quasar/manage/auditor/add";
	private final static String EDIT_AUDITOR_MAPPING_URL = "/admin/quasar/manage/auditor/{auditorId}";
	
	@Autowired
	private AuditorValidator createAuditorValidator;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuditorService auditorService;
	
	public AuditorController(){
		setTableItemsView("auditor-list");
		setEditFormView("auditor-edit");
		setViewName("auditor-add");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showAuditorList(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("auditors", auditorService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(value = ADD_AUDITOR_MAPPING_URL, method = RequestMethod.GET)
	public String createNewAuditor(ModelMap modelMap) {
		prepareCreateModel(modelMap, new AuditorForm());
		return getViewName();
	}
	
	@RequestMapping(value = ADD_AUDITOR_MAPPING_URL, method = RequestMethod.POST)
	public String processCreateAuditor(ModelMap modelMap, @Valid @ModelAttribute("auditorForm") AuditorForm form, BindingResult request ) {
		boolean isNotValid = request.hasErrors();
		if(!isNotValid){
			createAuditorValidator.validate(form, request);
		}
		if(isNotValid || request.hasErrors()){
			prepareCreateModel(modelMap, form);
			return getViewName();
		}
		final Long id = createNewAuditor(form);
		return getViewName();
	}
	
	
	@RequestMapping(value = EDIT_AUDITOR_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request, @PathVariable long auditorId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		Auditor form = new Auditor();
		/*if(userId != 0){
			form = eacCodeService.getById(userId);
			validateNotNull(form, userId);
		}*/
		
		return getEditFormView();
	}
	
	private Long createNewAuditor(final AuditorForm form){
		final Auditor auditor = form.getAuditor();
		userService.setUserPassword(auditor, form.getNewPassword());
		auditorService.createOrUpdate(auditor);
		return auditor.getId();
	}
	
	private void prepareCreateModel(ModelMap map, AuditorForm form){
		Map<String, Object> model = new HashMap<>();
		map.addAttribute("auditorForm", form);
		appendTabNo(model, TAB);
		appendModel(map, model);
	}
}
