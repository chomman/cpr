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
import cz.nlfnorm.quasar.entities.FieldOfEducation;
import cz.nlfnorm.quasar.services.FieldOfEducationService;


@Controller
public class FieldOfEducationController extends QuasarSupportController {
	
	private final static int TAB = 5; 
	private final static String LIST_MAPPING_URL = "/admin/quasar/manage/educations";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/education/{eId}";

	@Autowired
	private FieldOfEducationService fieldOfEducationService; 
	
	public FieldOfEducationController(){
		setTableItemsView("education-list");
		setEditFormView("education-edit");
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleEducationCourseList(ModelMap modelMap, HttpServletRequest request) {
		if(isDeleted(request)){
			appendSuccessDeleteParam(modelMap);
		}
		Map<String, Object> model = new HashMap<>();
		model.put("fieldOfEducations", fieldOfEducationService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.GET)
	public String handleEditFormView(ModelMap modelMap, HttpServletRequest request, @PathVariable long eId) throws ItemNotFoundException {
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		FieldOfEducation form = new FieldOfEducation();
		if(eId != 0){
			form = fieldOfEducationService.getById(eId);
			validateNotNull(form, "Can not dispaly NANDO code edit form, NANDO code was not found.");
		}
		prepareModel(modelMap, form);
		return getEditFormView();
	}
	
	@RequestMapping(value = FORM_MAPPING_URL + "/delete", method = RequestMethod.GET)
	public String handleRemove(ModelMap modelMap, HttpServletRequest request, @PathVariable long eId) throws ItemNotFoundException {
		final FieldOfEducation item = fieldOfEducationService.getById(eId);
		if(item != null){
			try{
				fieldOfEducationService.remove(item);
				return successDeleteRedirect(LIST_MAPPING_URL);
			}catch(Exception e){
				logger.warn("Field of education can not be delete id:" + eId, e);
			}
		}
		return "redirect:" + LIST_MAPPING_URL;
	}
	
	
	@RequestMapping(value = FORM_MAPPING_URL, method = RequestMethod.POST)
	public String handleSubmit(ModelMap modelMap, @Valid @ModelAttribute("command") FieldOfEducation form, BindingResult result) throws ItemNotFoundException{
		
		if(result.hasErrors()){
			prepareModel(modelMap, form);
			return getEditFormView();
		}
		final Long id = createOrUpdate(form);
		return successUpdateRedirect(FORM_MAPPING_URL.replace("{eId}", id+""));
	}
	
	
	
	private Long createOrUpdate(final FieldOfEducation form) throws ItemNotFoundException{
		FieldOfEducation educationCourse = null;
		if(form.getId() == null){
			educationCourse = new FieldOfEducation();
		}else{
			educationCourse = fieldOfEducationService.getById(form.getId());
			validateNotNull(educationCourse, "Could not create NANDO code. Not found.");
		}
		educationCourse.merge(form);
		fieldOfEducationService.createOrUpdate(educationCourse);
		return educationCourse.getId();
	}
	
	
	private void prepareModel(ModelMap map, FieldOfEducation form){
		Map<String, Object> model = new HashMap<>();
		appendTabNo(model, TAB);
		map.addAttribute("command", form);
		appendModel(map, model);
	}
}
