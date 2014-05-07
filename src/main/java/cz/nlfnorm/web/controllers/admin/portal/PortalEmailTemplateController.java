package cz.nlfnorm.web.controllers.admin.portal;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;

@Controller
public class PortalEmailTemplateController extends AdminSupportController {
	
	private final static String EDIT_MAPPING_URL = "/admin/portal/email-template/{id}";
	private final static String LIST_MAPPING_URL = "/admin/portal/email-templates";

	@Autowired
	private EmailTemplateService emailTemplateService;
	
	public PortalEmailTemplateController(){
		setTableItemsView("portal/email-templates");
		setEditFormView("portal/email-template-edit");
	}
	
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleOrderList(ModelMap map){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("emailTemplates", emailTemplateService.getAll());
		model.put("tab", 5);
		map.put("model", model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleSettingsEdit(ModelMap map, @PathVariable Long id) throws ItemNotFoundException{
		prepareModel(map, getTemplate(id));
		return getEditFormView();
	}
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String handleProcessSubmit(@Valid @ModelAttribute("emailTemplate") EmailTemplate template, BindingResult result, ModelMap map) throws ItemNotFoundException{
		if(result.hasErrors()){
			prepareModel(map, template);
			return getEditFormView();
		}
		update(template);
		map.put(SUCCESS_CREATE_PARAM, true);
		return getEditFormView();
	}
	
	
	private void update(final EmailTemplate form) throws ItemNotFoundException{
		Validate.notNull(form);
		final User user = UserUtils.getLoggedUser();
		EmailTemplate emailTemplate = null;
		if(form.getId() == null){
			emailTemplate = new EmailTemplate();
		}else{
			emailTemplate = getTemplate(form.getId());
		}
		
		if(user.isWebmaster()){
			emailTemplate.setName(form.getName());
			emailTemplate.setCode(form.getCode());
			emailTemplate.setVariables(form.getVariables());
		}
		emailTemplate.setSubject(form.getSubject());
		emailTemplate.setBody(form.getBody());
		emailTemplateService.createOrUpdate(emailTemplate);
	}
	
	
	
	private void prepareModel(ModelMap map, final EmailTemplate emailTemplate){
		map.addAttribute("emailTemplate", emailTemplate);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", 5);
		map.put("model", model);
	}
	
	private EmailTemplate getTemplate(final Long id) throws ItemNotFoundException{
		if(id == 0){
			return new EmailTemplate();
		}
		EmailTemplate emailTemplate = emailTemplateService.getById(id);
		if(emailTemplate == null){
			throw new ItemNotFoundException("EmailTemplate was not found. [id="+id+"]");
		}
		return emailTemplate;
	}
}
