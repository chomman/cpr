package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.services.AuditorService;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
@Controller
public class AuditorController extends QuasarSupportController {
	
	private final static int TAB = 3; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/auditors";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/auditor/{auditorId}";
	
	
	@Autowired
	private AuditorService auditorService;
	
	public AuditorController(){
		setTableItemsView("user-list");
		setEditFormView("user-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showNandoCodes(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("eacCodes", auditorService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request, @PathVariable long auditorId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		Auditor form = new Auditor();
		/*if(userId != 0){
			form = eacCodeService.getById(userId);
			validateNotNull(form, userId);
		}*/
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	private void prepareModel(ModelMap map, Auditor form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		map.addAttribute("auditor", form);
		appendModel(map, model);
	}
}
