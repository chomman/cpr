package cz.nlfnorm.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.entities.StandardCategory;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.services.RegulationService;
import cz.nlfnorm.services.StandardCategoryService;

@Controller
public class StandardCategoryController extends CprSupportController{
	
	private final static int TAB = 13;
	
	private final static String REGULATION_ID_PARAM = "iid";
	private final static String ACTION_PARAM = "action";
	private final static String ACTION_ASSIGN = "assign";
	private final static String ACTION_UNASSIGN = "unassign";
	private final static String LIST_MAPPING_URL = "/admin/cpr/standard-categories";
	private final static String FORM_MAPPING_URL = "/admin/cpr/standard-category/{id}";
	
	@Autowired
	private StandardCategoryService standardCategoryService;
	@Autowired
	private RegulationService regulationService;
	
	public StandardCategoryController(){
		setTableItemsView("standard-category-list");
		setEditFormView("standard-category-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handlePartnerListPage(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("categories", standardCategoryService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String handlePartnerEditPage(ModelMap modelMap, HttpServletRequest request, @PathVariable long id) throws ItemNotFoundException, PageNotFoundEception {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		StandardCategory form = new StandardCategory();
		if(id != 0){
			form = standardCategoryService.getById(id);
			Validate.notNull(form);
			processAction(request, form);
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String handleSubmit(ModelMap modelMap, @Valid @ModelAttribute StandardCategory form, BindingResult result) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{id}", id+""));
	}
	
	
	private Long createOrUpdate(final StandardCategory form) throws ItemNotFoundException{
		StandardCategory stamdardCategory = null;
		if(form.getId() == null){
			stamdardCategory = new StandardCategory();
		}else{
			stamdardCategory = standardCategoryService.getById(form.getId());
			Validate.notNull(stamdardCategory);
		}
		stamdardCategory.setNameCzech(form.getNameCzech());
		stamdardCategory.setNameEnglish(form.getNameEnglish());
		stamdardCategory.setCode(form.getCode());
		stamdardCategory.setNoaoUrl(form.getNoaoUrl());
		stamdardCategory.setOjeuPublicationCzech(form.getOjeuPublicationCzech());
		stamdardCategory.setOjeuPublicationEnglish(form.getOjeuPublicationEnglish());
		stamdardCategory.setSpecializationCzech(form.getSpecializationCzech());
		stamdardCategory.setSpecializationEnglish(form.getSpecializationEnglish());
		standardCategoryService.createOrUpdate(stamdardCategory);
		return stamdardCategory.getId();
	}
	
	private void prepareModel(final ModelMap map, final StandardCategory form){
		Map<String, Object> model = new HashMap<>();
		if(form.getId() != null){
			model.put("regulations", standardCategoryService.getAllUnassignedRegulationFor(form));
		}
		appendTabNo(model, TAB);
		map.addAttribute("standardCategory", form);
		appendModel(map, model);
	}
	
	private void processAction(final HttpServletRequest request,final StandardCategory standardCategory){
		final String action  = getAction(request);
		if(StringUtils.isNotBlank(action)){
			final Regulation regulation = getRegulationId(request);
			if(regulation != null && action.equals(ACTION_ASSIGN)){
				standardCategory.addRegulation(regulation);
				standardCategoryService.update(standardCategory);
			}
			if(regulation != null && action.equals(ACTION_UNASSIGN)){
				standardCategory.removeRegulation(regulation);
				standardCategoryService.update(standardCategory);
			}
		}
	}
	
	private String getAction(final HttpServletRequest request){
		return request.getParameter(ACTION_PARAM);
	}
	
	private Regulation getRegulationId(final HttpServletRequest request){
		final String id = request.getParameter(REGULATION_ID_PARAM);
		if(StringUtils.isNotBlank(id)){
			return regulationService.getById(Long.valueOf(id));
		}
		return null;
	}
}
