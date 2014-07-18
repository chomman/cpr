
package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.entities.CertificationBody;
import cz.nlfnorm.quasar.enums.AuditLogItemType;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.AuditLogItemService;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.CertificationBodyService;
import cz.nlfnorm.quasar.web.forms.AuditLogItemForm;
import cz.nlfnorm.quasar.web.validators.AuditorLogItemValidator;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;

@Controller
public class AuditLogController extends LogControllerSupport {
	
	private final static int TAB = 8;

	private final static String LIST_MAPPING_URL = "/admin/quasar/audit-logs";
	public  final static String EDIT_MAPPING_URL = "/admin/quasar/audit-log/{id}";
	private final static String AUDIT_LOG_ITEM_DELETE_URL = "/admin/quasar/audit-log-item/delete/{id}";
	
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private AuditLogItemService auditLogItemService;
	@Autowired
	private CertificationBodyService certificationBodyService;
	
	
	@Autowired
	private AuditorLogItemValidator auditorLogItemValidator;
	
	public AuditLogController(){
		setTableItemsView("audit-log-list");
		setEditFormView("audit-log-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(CertificationBody.class, new IdentifiableByLongPropertyEditor<CertificationBody>( certificationBodyService ));
	}
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleAdminAuditLogs(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = handlePageRequest(request, auditLogService, LIST_MAPPING_URL);
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
		
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleAuditLogEdit(ModelMap modelMap, @PathVariable long id, HttpServletRequest request) throws ItemNotFoundException{
		if(id == 0){
			id = auditLogService.createNewToLoginedUser();
			return successUpdateRedirect(EDIT_MAPPING_URL.replace("{id}", id+""));
		}
		if(isDeleted(request)){
			appendSuccessCreateParam(modelMap);
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		final AuditLog log = getAuditLog(id);
		prepareModelFor(log, modelMap, getItem(request, log), isLogItemIdSet(request));
		return getEditFormView();
	}
	
	
	
	@RequestMapping(value = AUDIT_LOG_ITEM_DELETE_URL)
	public String handleAuditLogEdit(final @PathVariable long id) throws ItemNotFoundException{
		final AuditLogItem auditLogItem = getAuditLogItem(id);
		final Long auditLogId = auditLogItem.getAuditLog().getId();
		auditLogService.updateAndSetChanged(auditLogItem.getAuditLog());
		auditLogItemService.delete(auditLogItem);
		return successDeleteRedirect(EDIT_MAPPING_URL.replace("{id}", auditLogId+""));
	}
		
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmitAuditLogItem(
			ModelMap modelMap, 
			@Valid @ModelAttribute(COMMAND) AuditLogItemForm form,
			BindingResult result,
			@PathVariable long id, 
			HttpServletRequest request) throws ItemNotFoundException{
		final AuditLog auditLog = getAuditLog(id);
		boolean showForm = true;
		boolean hasErrors = result.hasErrors();
		if(!hasErrors){
			auditorLogItemValidator.validate(form, result);
			hasErrors = result.hasErrors();
			if(!hasErrors){
				createOrUpdate(auditLog, form, modelMap);
				appendSuccessCreateParam(modelMap);
				showForm = false;
				form = getItem(request, auditLog);
			}
		}
		if(hasErrors){
			setEacCodes(form, form.getItem());
			setNandoCodes(form, form.getItem());
		}
		prepareModelFor(auditLog, modelMap, form, showForm);
		return getEditFormView();
	}
	
	private void createOrUpdate(final AuditLog auditLog, final AuditLogItemForm form, ModelMap modelMap) throws ItemNotFoundException{
		if(!auditLog.isEditable()){
			throw new AccessDeniedException("Auditlog is not editable." + auditLog + UserUtils.getLoggedUser());
		}
		AuditLogItem auditLogItem = null; 
		if(form.getItem().getId() == null){
			auditLogItem = form.getItem();
			auditLogItem.setAuditLog(auditLog);
		}else{
			auditLogItem = getAuditLogItem(form.getItem().getId());
		}
		if(setAndCreateCompany(form, auditLogItem)){
			// Some company with given name was found and used.
			modelMap.put("companyFound", true);
		}
		setAndCreateCertificationBody(form, auditLogItem);
		setEacCodes(form, auditLogItem);
		setNandoCodes(form, auditLogItem);
		auditLogItem.setAuditDate(form.getItem().getAuditDate());
		auditLogItem.setDurationInDays(form.getItem().getDurationInDays());
		auditLogItem.setCertifiedProduct(form.getItem().getCertifiedProduct());
		auditLogItem.setType(form.getItem().getType());
		auditLogItemService.createOrUpdate(auditLogItem);		
	}
	
	
	
	
	private void setAndCreateCertificationBody(final AuditLogItemForm form, final AuditLogItem item){
		if(form.getItem().getCertificationBody() != null){
			item.setCertificationBody(form.getItem().getCertificationBody());
			return;
		}
		if(StringUtils.isNotBlank(form.getCertificationBodyName())){
			CertificationBody certificationBody = certificationBodyService.findByName(form.getCertificationBodyName());
			if(certificationBody != null){
				item.setCertificationBody(certificationBody);
			}else{
				certificationBody = new CertificationBody(StringUtils.trim(form.getCertificationBodyName()));
				certificationBodyService.create(certificationBody);
				item.setCertificationBody(certificationBodyService.findByName(form.getCertificationBodyName()));
			}
		}
	}
	
	
	private AuditLogItemForm getItem(HttpServletRequest request, final AuditLog auditLog){
		final Long id = getItemId(request);
		if(id == null || id == 0){
			return new AuditLogItemForm(auditLog);
		}
		return new AuditLogItemForm(auditLog, auditLogItemService.getById( id ));
	}
	
	private void prepareModelFor(final AuditLog log, ModelMap modelMap,final AuditLogItemForm form, final boolean showForm) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		model.put("log", log);
		model.put("statusType", ChangeLogStatusController.ACTION_AUDIT_LOG);
		model.put("showForm", showForm);
		model.put("auditLogItemTypes", AuditLogItemType.getAll());
		model.put("dateThreshold", auditLogService.getEarliestPossibleDateForAuditLog(log.getAuditor()));
		if(log.getStatus().equals(LogStatus.APPROVED)){
			model.put("totals", auditLogService.getTotalsFor(log));
		}
		modelMap.addAttribute(COMMAND, form);
		appendModel(modelMap, model);
	}
	
	
	
	
	
	private AuditLog getAuditLog(final long id) throws ItemNotFoundException{
		final AuditLog auditLog = auditLogService.getById(id);
		if(auditLog == null){
			throw new ItemNotFoundException();
		}
		AccessUtils.validateAuthorizationFor(auditLog);
		return auditLog;
	}
	
	
	private AuditLogItem getAuditLogItem(final long id) throws ItemNotFoundException{
		final AuditLogItem item  = auditLogItemService.getById(id);
		if(item == null){
			throw new ItemNotFoundException("AuditLogItem [id="+id+"] not found");
		}
		AccessUtils.validateAuthorizationFor(item.getAuditLog());
		return item;
	}
	
	
	
	
	
}
