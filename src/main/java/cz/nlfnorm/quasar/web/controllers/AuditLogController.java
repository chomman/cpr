package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
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

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.AuditLogItem;
import cz.nlfnorm.quasar.entities.CertificationBody;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.EacCode;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.enums.AuditLogItemType;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditLogItemService;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.CertificationBodyService;
import cz.nlfnorm.quasar.services.CompanyService;
import cz.nlfnorm.quasar.services.EacCodeService;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.web.forms.AuditLogItemForm;
import cz.nlfnorm.quasar.web.validators.AuditorLogItemValidator;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;

@Controller
public class AuditLogController extends QuasarSupportController {
	
	private final static int TAB = 8;
	private final static String ITEM_ID_PARAM_NAME = "iid";
	private final static String ADMIN_LIST_MAPPING_URL = "/admin/quasar/manage/audit-logs";
	private final static String PROFILE_LIST_MAPPING_URL = "/admin/quasar/audit-logs";
	private final static String PROFILE_EDIT_MAPPING_URL = "/admin/quasar/audit-log/{id}";
	private final static String AUDIT_LOG_ITEM_DELETE_URL = "/admin/quasar/audit-log-item/delete/{id}";

	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private AuditLogItemService auditLogItemService;
	@Autowired
	private CertificationBodyService certificationBodyService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private EacCodeService eacCodeService;
	@Autowired
	private NandoCodeService nandoCodeService;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private AuditorLogItemValidator auditorLogItemValidator;
	
	public AuditLogController(){
		setTableItemsView("audit-log-list");
		setEditFormView("audit-log-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdentifiableByLongPropertyEditor<Company>( companyService ));
		binder.registerCustomEditor(CertificationBody.class, new IdentifiableByLongPropertyEditor<CertificationBody>( certificationBodyService ));
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	@RequestMapping(ADMIN_LIST_MAPPING_URL)
	public String handleAdminAuditLogs(ModelMap modelMap, HttpServletRequest request) {
		handlePageRequest(modelMap, request, RequestUtils.getRequestParameterMap(request));
		return getTableItemsView();
	}
	
	@RequestMapping(PROFILE_LIST_MAPPING_URL)
	public String handleProfileAuditLogs(ModelMap modelMap, HttpServletRequest request) {
		final Map<String, Object> citeria = RequestUtils.getRequestParameterMap(request);
		citeria.put(AuditorFilter.AUDITOR, UserUtils.getLoggedUser().getId());
		handlePageRequest(modelMap, request, citeria);
		return getViewDir() + "profile/auditor-audit-logs";
	}
	
	
	@RequestMapping(value = PROFILE_EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleAuditLogEdit(ModelMap modelMap, @PathVariable long id, HttpServletRequest request) throws ItemNotFoundException{
		if(id == 0){
			id = auditLogService.createNewToLoginedUser();
			return successUpdateRedirect(PROFILE_EDIT_MAPPING_URL.replace("{id}", id+""));
		}
		if(isDeleted(request)){
			appendSuccessCreateParam(modelMap);
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		prepareModelFor(getAuditLog(id), modelMap, getItem(request), isAuditLogItemIdSet(request));
		return getViewDir() + "profile/auditor-audit-log-edit";
	}
	
	
	
	@RequestMapping(value = AUDIT_LOG_ITEM_DELETE_URL)
	public String handleAuditLogEdit(final @PathVariable long id) throws ItemNotFoundException{
		final AuditLogItem auditLogItem = getAuditLogItem(id);
		final Long auditLogId = auditLogItem.getAuditLog().getId();
		auditLogService.updateAndSetChanged(auditLogItem.getAuditLog());
		auditLogItemService.delete(auditLogItem);
		return successDeleteRedirect(PROFILE_EDIT_MAPPING_URL.replace("{id}", auditLogId+""));
	}
	
	
	@RequestMapping(value = PROFILE_EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmitAuditLogItem(
			ModelMap modelMap, 
			@Valid @ModelAttribute(COMMAND) AuditLogItemForm form,
			BindingResult result,
			@PathVariable long id, 
			HttpServletRequest request) throws ItemNotFoundException{
		final AuditLog auditLog = getAuditLog(id);
		boolean showForm = true;
		if(!result.hasErrors()){
			auditorLogItemValidator.validate(form, result);
			if(!result.hasErrors()){
				createOrUpdate(auditLog, form);
				appendSuccessCreateParam(modelMap);
				showForm = false;
				form = new AuditLogItemForm();
			}
		}
		prepareModelFor(auditLog, modelMap, form, showForm);
		return getViewDir() + "profile/auditor-audit-log-edit";
	}
	
	private void createOrUpdate(final AuditLog auditLog, final AuditLogItemForm form) throws ItemNotFoundException{
		AuditLogItem auditLogItem = null; 
		if(form.getItem().getId() == null){
			auditLogItem = form.getItem();
			auditLogItem.setAuditLog(auditLog);
		}else{
			auditLogItem = getAuditLogItem(form.getItem().getId());
		}
		setAndCreateCompany(form, auditLogItem);
		setAndCreateCertificationBody(form, auditLogItem);
		setEacCodes(form, auditLogItem);
		setNandoCodes(form, auditLogItem);
		auditLogItem.setAuditDate(form.getItem().getAuditDate());
		auditLogItem.setDurationInDays(form.getItem().getDurationInDays());
		auditLogItem.setCertifiedProduct(form.getItem().getCertifiedProduct());
		auditLogItem.setType(form.getItem().getType());
		auditLogItemService.createOrUpdate(auditLogItem);		
	}
	
	private boolean isAuditLogItemIdSet(HttpServletRequest request){
		return request.getParameter(ITEM_ID_PARAM_NAME) != null;
	}
	
	private void setEacCodes(final AuditLogItemForm form, final AuditLogItem item){
		item.getEacCodes().clear();
		if( StringUtils.isNotBlank(form.getEacCodes()) ){
			final String[] codeList = form.getEacCodes().split(",");
			for(final String code : codeList){
				final EacCode eacCode = eacCodeService.getByCode(code);
				Validate.notNull(eacCode);
				item.getEacCodes().add(eacCode);
			}
		}
	}
	
	private void setNandoCodes(final AuditLogItemForm form, final AuditLogItem item){
		item.getNandoCodes().clear();
		if( StringUtils.isNotBlank(form.getNandoCodes()) ){
			final String[] codeList = form.getNandoCodes().split(",");
			for(final String code : codeList){
				final NandoCode nandoCode = nandoCodeService.getByNandoCode(code);
				Validate.notNull(nandoCode);
				item.getNandoCodes().add(nandoCode);
			}
		}
	}
	
	private void setAndCreateCompany(final AuditLogItemForm form, final AuditLogItem item){
		if(form.getItem().getCompany() != null){
			item.setCompany(form.getItem().getCompany());
			return;
		}
		if(StringUtils.isNotBlank(form.getCompanyName())){
			Company company = companyService.findByName(form.getCompanyName());
			if(company != null){
				item.setCompany(company);
			}else{
				company = new Company(StringUtils.trim(form.getCompanyName()));
				companyService.create(company);
				item.setCompany(companyService.findByName(form.getCompanyName()));
			}
		}
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
	
	
	private AuditLogItemForm getItem(HttpServletRequest request){
		final String val = request.getParameter(ITEM_ID_PARAM_NAME);
		if(StringUtils.isBlank(val)){
			return new AuditLogItemForm();
		}
		return new AuditLogItemForm( auditLogItemService.getById(Long.valueOf(val)) );
	}
	
	private void prepareModelFor(final AuditLog log, ModelMap modelMap,final AuditLogItemForm form, final boolean showForm) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		model.put("auditLog", log);
		model.put("showForm", showForm);
		model.put("auditLogItemTypes", AuditLogItemType.getAll());
		model.put("dateThreshold", auditLogService.getEarliestPossibleDateForAuditLog());
		modelMap.addAttribute(COMMAND, form);
		appendModel(modelMap, model);
	}
	
	
	
	private void handlePageRequest(ModelMap modelMap, HttpServletRequest request,final Map<String, Object> citeria){
		Map<String, Object> model = new HashMap<>();
		final int currentPage = RequestUtils.getPageNumber(request);
		final PageDto page = auditLogService.getPage(citeria, currentPage);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request, citeria, page.getCount(), "/admin/quasar/manage/auditors"));
			model.put("logs", page.getItems());
		}
		model.put("statuses", LogStatus.getAll());
		model.put("params", citeria);
		model.put("partners", partnerService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
	}
	
	private AuditLog getAuditLog(final long id) throws ItemNotFoundException{
		final AuditLog auditLog = auditLogService.getById(id);
		if(auditLog == null){
			throw new ItemNotFoundException();
		}
		checkAuthorization(auditLog);
		return auditLog;
	}
	
	
	private AuditLogItem getAuditLogItem(final long id) throws ItemNotFoundException{
		final AuditLogItem item  = auditLogItemService.getById(id);
		if(item == null){
			throw new ItemNotFoundException("AuditLogItem [id="+id+"] not found");
		}
		checkAuthorization(item.getAuditLog());
		return item;
	}
	
	private void checkAuthorization(final AuditLog log){
		if(!isLoggedUserAuthorizedTo(log)){
			throw new AccessDeniedException(UserUtils.getLoggedUser() +  " tried to access " + log);
		}
	}
	
	private boolean isLoggedUserAuthorizedTo(final AuditLog auditLog){
		final User user = UserUtils.getLoggedUser();
		if(user.isQuasarAdmin() || user.getId().equals(auditLog.getAuditor().getId())){
			return true;
		}
		return false;
	}
	
}
