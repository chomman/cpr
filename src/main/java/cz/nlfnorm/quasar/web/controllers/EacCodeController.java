package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.services.EacCodeService;

@Controller
public class EacCodeController extends QuasarSupportController {
	
	private final static int TAB = 2; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/eac-codes";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/eac-code/{codeId}";
	
	
	@Autowired
	private EacCodeService eacCodeService;
	
	public EacCodeController(){
		setTableItemsView("eac-code-list");
		setEditFormView("eac-code-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String showNandoCodes(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("eacCodes", eacCodeService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String showEditForm(ModelMap modelMap, HttpServletRequest request, @PathVariable long codeId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		EacCode form = new EacCode();
		if(codeId != 0){
			form = eacCodeService.getById(codeId);
			validateNotNull(form, "Can not show EAC code edit form. EAC code not found.");
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap, @Valid @ModelAttribute("eacCode") EacCode form, BindingResult result) throws ItemNotFoundException{
		
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final Long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{codeId}", id+""));
	}
	
	@RequestMapping(value = "/ajax/eac-codes", method = RequestMethod.GET)
	public @ResponseBody List<EacCode>  getEacCodes(@RequestBody @RequestParam(required = false, value = "term") String query){
		return eacCodeService.getAllForQsAuditor();
	}
	
	private Long createOrUpdate(final EacCode form) throws ItemNotFoundException{
		EacCode eacCode = null;
		if(form.getId() == null){
			eacCode = new EacCode();
		}else{
			eacCode = eacCodeService.getById(form.getId());
			validateNotNull(eacCode, "Could not update EAC code. Not found.");
		}
		eacCode.setCode(StringUtils.trim(form.getCode()));
		eacCode.setEnabled(form.isEnabled());
		eacCode.setName(StringUtils.trim(form.getName()));
		eacCode.setNaceCode(StringUtils.trim(form.getNaceCode()));
		eacCode.setForQsAuditor(form.isForQsAuditor());
		eacCode.setThreshold(form.getThreshold());
		eacCode.setAuditsInTrainingTreshold(form.getAuditsInTrainingTreshold());
		eacCodeService.createOrUpdate(eacCode);
		return eacCode.getId();
	}
	
	private void prepareModel(ModelMap map, EacCode form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		map.addAttribute("eacCode", form);
		appendModel(map, model);
	}
		
	
	
}
