package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.entities.CommissionDecision;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.CommissionDecisionService;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;

@Controller
public class CommissionDecisionConteroller extends SupportAdminController {
	
	private static final int CPR_TAB_INDEX = 9;	
	public static final String BASE_URL = "/admin/cpr/commission-decisions";
	private final static String SUCCESS_DELETE_PARAM = "successDelete";
	
	
	@Autowired
	private CommissionDecisionService commissionDecisionService;

	
	public CommissionDecisionConteroller(){
		setTableItemsView("cpr/commission-decision-list");
		setEditFormView("cpr/commission-decision-edit");
	}
	
	
	@RequestMapping(BASE_URL)
	public String showList(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> model = prepareModel();
		String deleted = request.getParameter(SUCCESS_DELETE_PARAM);
		if(deleted != null && deleted.equals("1")){
			model.put(SUCCESS_DELETE_PARAM, true);
		}else if(deleted != null && deleted.equals("0")){
			model.put(SUCCESS_DELETE_PARAM, false);
		}
		model.put("commissionDecisions", commissionDecisionService.getAll());
		modelMap.put("model", model);
		return getTableItemsView();
	}
	
	
	
	@RequestMapping(value = BASE_URL + "/edit/{id}",  method = RequestMethod.GET)
	public String showEditForm(@PathVariable Long id, ModelMap modelMap) throws ItemNotFoundException{
		
		CommissionDecision form = null;
		if(id == null || id == 0){
			form = createEmptyForm();
		}else{
			form = commissionDecisionService.getById(id);
			if(form == null){
				createItemNotFoundError("Commision decision with ID "+ id +"  was not found");
			}
		}
		prepareModelMap(modelMap, form);
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = BASE_URL + "/edit/{cdId}",  method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap, @Valid CommissionDecision command, BindingResult result) throws ItemNotFoundException{
		if(!result.hasErrors()){
			createOrUpdate(command);
			modelMap.put("success", true);
		}
		prepareModelMap(modelMap, command);
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = BASE_URL + "/delete/{id}")
	public String remove(ModelMap modelMap,  @PathVariable Long id ) throws ItemNotFoundException{
		
		CommissionDecision commissionDecision = commissionDecisionService.getById(id);
		if(commissionDecision == null){
			createItemNotFoundError("Commision decision was not found [id="+id+"]");
		}
		try{
			commissionDecisionService.delete(commissionDecision);
			return "redirect:" + BASE_URL + "?" + SUCCESS_DELETE_PARAM + "=1";
		}catch(Exception e){
			
		}
		return "redirect:" + BASE_URL + "?" + SUCCESS_DELETE_PARAM + "=0"; 
	}
	
	
	private Long createOrUpdate(CommissionDecision form) throws ItemNotFoundException{
		CommissionDecision model = null;
		if(form.getId() == null || form.getId() == 0){
			model = new CommissionDecision();
		}else{
			model = commissionDecisionService.getById(form.getId());
			if(model == null){
				createItemNotFoundError("Unable to update commission decision id: "  + form.getId());
			}
		}
		model.setCzechFileUrl(form.getCzechFileUrl());
		model.setEnglishFileUrl(form.getEnglishFileUrl());
		model.setCzechLabel(form.getCzechLabel());
		model.setEnglishLabel(form.getEnglishLabel());
		model.setDraftAmendmentUrl(form.getDraftAmendmentUrl());
		commissionDecisionService.saveOrUpdate(model);
		form.setId(model.getId());
		return model.getId();
	}
	
	
	
	
	private Map<String, Object> prepareModel(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", CPR_TAB_INDEX);
		model.put("url", BASE_URL);
		return model;
	}
	
	private Map<String, Object> prepareModel(CommissionDecision form){
		Map<String, Object> model = prepareModel();
		model.put("id", form.getId());
		return model;
	}
	private void prepareModelMap(ModelMap modelMap, CommissionDecision form){
		modelMap.put("model", prepareModel(form));
		modelMap.addAttribute("commissionDecision", form);
	}
	
	private CommissionDecision createEmptyForm(){
		CommissionDecision form = new CommissionDecision();
		form.setId(0L);
		return form;
	}
}
