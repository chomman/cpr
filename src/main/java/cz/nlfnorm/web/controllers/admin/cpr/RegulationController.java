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
import cz.nlfnorm.entities.RegulationContent;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.exceptions.PageNotFoundEception;
import cz.nlfnorm.services.RegulationService;

@Controller
public class RegulationController extends CprSupportController{
	
	private final static int TAB = 12;
	
	private final static String TYPE_PARAM = "type";
	private final static String ACTION_PARAM = "action";
	
	private final static int CREATE = 1;
	private final static int DELETE = 0;
	
	private final static String LIST_MAPPING_URL = "/admin/cpr/regulations";
	private final static String FORM_MAPPING_URL = "/admin/cpr/regulation/{id}";
	
	@Autowired
	private RegulationService regulationService;
	
	public RegulationController(){
		setTableItemsView("regulation-list");
		setEditFormView("regulation-edit");
	}
	
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handlePartnerListPage(ModelMap modelMap) {
		Map<String, Object> model = new HashMap<>();
		model.put("regulations", regulationService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String handlePartnerEditPage(ModelMap modelMap, HttpServletRequest request, @PathVariable long id) throws ItemNotFoundException, PageNotFoundEception {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		Regulation form = new Regulation();
		if(id != 0){
			form = regulationService.getById(id);
			Validate.notNull(form);
			processAction(form, request);
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	private void processAction(final Regulation regulation, HttpServletRequest request) throws PageNotFoundEception{
		final String localeType = request.getParameter(TYPE_PARAM);
		if(StringUtils.isNotBlank(localeType) && Regulation.isAvaiable(localeType)){
			final String action = request.getParameter(ACTION_PARAM);
			if(StringUtils.isNotBlank(action)){
				if(Integer.valueOf(action) == CREATE){
					if(!regulation.getLocalized().containsKey(localeType)){
						regulation.getLocalized().put(localeType, new RegulationContent());
						regulationService.update(regulation);
					}
				}else if(Integer.valueOf(action) == DELETE){
					if(regulation.getLocalized().containsKey(localeType)){
						regulation.getLocalized().remove(localeType);
						regulationService.update(regulation);
					}
				}
			}
		}
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String handleSubmit(ModelMap modelMap, @Valid @ModelAttribute Regulation form, BindingResult result) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{id}", id+""));
	}
	
	private Long createOrUpdate(final Regulation form) throws ItemNotFoundException{
		Regulation regulation = null;
		if(form.getId() == null){
			regulation = new Regulation();
		}else{
			regulation = regulationService.getById(form.getId());
			Validate.notNull(regulation);
		}
		regulation.setLocalized(form.getLocalized());
		regulation.setCode(form.getCode());
		regulationService.createOrUpdate(regulation);
		return regulation.getId();
	}
	
	private void prepareModel(final ModelMap map, final Regulation form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		map.addAttribute("regulation", form);
		appendModel(map, model);
	}
	
}
