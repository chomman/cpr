package sk.peterjurkovic.cpr.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.constants.DopTextVariable;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.AssessmentSystemService;


@Controller
public class CprAssessmentSystemController extends SupportAdminController {
	
	
	public static final int CPR_TAB_INDEX = 4;
	
	@Autowired
	private AssessmentSystemService assessmentSystemService;
	
	
	
	public CprAssessmentSystemController(){
		setTableItemsView("cpr-assessmentsystems");
		setEditFormView("cpr-assessmentsystems-edit");
	}
	
	
	/**
	 * Zobrazi systemy posudzovania zhody
	 * 
	 * @param ModelMap model
	 * @return String view
	 */
	@RequestMapping("/admin/cpr/assessmentsystems")
    public String showAssessmentSystem(ModelMap modelMap) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<AssessmentSystem> systems = assessmentSystemService.getAllAssessmentSystems();
		model.put("systems", systems);
		model.put("tab", CPR_TAB_INDEX);
		modelMap.put("model", model);
        return getTableItemsView();
    }
	
	
	/**
	 * Odstrani system posudzovania, na zaklade daneho ID
	 * 
	 * @param Lond, ID systemu posudzovania, ktory ma byt odstraneny 
	 * @param ModelMap model
	 * @return String JSP stranka 
	 */
	@RequestMapping( value = "/admin/cpr/assessmentsystems/delete/{assessmentSystemId}", method = RequestMethod.GET)
	public String deleteAssessmentSystem(@PathVariable Long assessmentSystemId,  ModelMap modelMap) {
						
		AssessmentSystem assessmentSystem = assessmentSystemService.getAssessmentSystemById(assessmentSystemId);
		if(assessmentSystem == null){
			modelMap.put("notFoundError", true);
			return getTableItemsView();
		}
		assessmentSystemService.remove(assessmentSystem);
		modelMap.put("successDelete", true);
        return showAssessmentSystem(modelMap);
	}
	
	
	
	
	
	@RequestMapping( value = "/admin/cpr/assessmentsystems/edit/{assessmentSystemId}", method = RequestMethod.GET)
	public String showForm(@PathVariable Long assessmentSystemId,  ModelMap model) {
						
		AssessmentSystem form = null;
		// vytvorenie novej polozky
		if(assessmentSystemId == 0){
			form = createEmptyForm();
		}else{
			// editacia polozky
			form = assessmentSystemService.getAssessmentSystemById(assessmentSystemId);
			if(form == null){
				model.put("notFoundError", true);
				return getEditFormView();
			}
		}
		prepareModel(form, model, assessmentSystemId);
        return getEditFormView();
	}
	
	
	
	/**
	 * Spracuje poziadavku, ktora upravi, resp vytovri novy system posudzovania, v pripade ak je assessmentSystemID 0, 
	 * jedna sa o udalost pridania noveho systemu, inak o editaciu
	 * 
	 * @param Long assessmentSystemId
	 * @param AssessmentSystem webovy formular 
	 * @param result
	 * @param Model model
	 * @return String JSP stranka
	 * @throws ItemNotFoundException, ak dany system nie je evidovany
	 */
	@RequestMapping( value = "/admin/cpr/assessmentsystems/edit/{assessmentSystemId}", method = RequestMethod.POST)
	public String processSubmit(@PathVariable Long assessmentSystemId,  @Valid  AssessmentSystem form, BindingResult result, ModelMap model) throws ItemNotFoundException {

		if (result.hasErrors()) {
			prepareModel(form, model, assessmentSystemId);
        }else{
        	createOrUpdate(form);
        	model.put("successCreate", true);
        	if(assessmentSystemId == 0){
        		form = createEmptyForm();
        		prepareModel(form, model, assessmentSystemId);
        	}
        }
		
        return getEditFormView();
	}
	
	
	
	
	
	private void prepareModel(AssessmentSystem form, ModelMap map, Long assessmentSystemId){
		Map<String, Object> model = new HashMap<String, Object>();
		map.addAttribute("assessmentSystem", form);
		model.put("assessmentSystemId", assessmentSystemId);
		model.put("tab", CPR_TAB_INDEX);
		model.put("varId",DopTextVariable.VAR_NOAO_ID);
		model.put("varAonoName",DopTextVariable.VAR_NOAO_NAME);
		model.put("varReport",DopTextVariable.VAR_REPORT);
		map.put("model", model); 
	}
	
	
	
	
	private void createOrUpdate(AssessmentSystem form) throws ItemNotFoundException{
		AssessmentSystem assessmentSystem = null;
			
		if(form.getId() == null || form.getId() == 0){
			assessmentSystem = new AssessmentSystem();
		}else{
			assessmentSystem = assessmentSystemService.getAssessmentSystemById(form.getId());
			if(assessmentSystem == null){
				createItemNotFoundError("Položka s ID: " + form.getId() + " se v systému nenachází");
			}
		}
		
		assessmentSystem.setAssessmentSystemCode(form.getAssessmentSystemCode());
		assessmentSystem.setName(form.getName());
		assessmentSystem.setDescription(form.getDescription());
		assessmentSystem.setEnabled(form.getEnabled());
		assessmentSystem.setDeclarationOfPerformanceText(form.getDeclarationOfPerformanceText().trim().replaceAll("(\\')*", ""));
		assessmentSystemService.saveOrUpdateAssessmentSystem(assessmentSystem);
	}
	
	private AssessmentSystem createEmptyForm(){
		AssessmentSystem form = new AssessmentSystem();
		form.setId(0L);
		return form;
	}
	
}
