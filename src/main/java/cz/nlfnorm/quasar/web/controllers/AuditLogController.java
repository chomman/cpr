package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.AuditLog;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.EducationLevel;
import cz.nlfnorm.quasar.entities.Experience;
import cz.nlfnorm.quasar.entities.FieldOfEducation;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.services.AuditLogItemService;
import cz.nlfnorm.quasar.services.AuditLogService;
import cz.nlfnorm.quasar.services.CompanyService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.web.forms.AuditLogItemForm;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;

@Controller
public class AuditLogController extends QuasarSupportController {
	
	private final static int TAB = 8;
	private final static String ID_PARAM_NAME = "iid";
	private final static String ADMIN_LIST_MAPPING_URL = "/admin/quasar/manage/audit-logs";
	private final static String PROFILE_LIST_MAPPING_URL = "/admin/quasar/audit-logs";
	private final static String PROFILE_EDIT_MAPPING_URL = "/admin/quasar/audit-log/{id}";
	private final static String FORM_MAPPING_URL = "/admin/quasar/manage/audit-log/{id}";

	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private AuditLogItemService auditLogItemService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private LocalDateEditor localDateEditor;
	
	public AuditLogController(){
		setTableItemsView("audit-log-list");
		setEditFormView("audit-log-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdentifiableByLongPropertyEditor<Company>( companyService ));
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
	
	@RequestMapping(PROFILE_EDIT_MAPPING_URL)
	public String handleAuditLogEdit(ModelMap modelMap, @PathVariable long id, HttpServletRequest request) throws ItemNotFoundException{
		if(id == 0){
			id = auditLogService.createNewToLoginedUser();
			return successUpdateRedirect(PROFILE_EDIT_MAPPING_URL.replace("{id}", id+""));
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		prepareModelFor(id, modelMap, getItem(request));
		return getViewDir() + "profile/auditor-audit-log-edit";
	}
	
	
	private AuditLogItemForm getItem(HttpServletRequest request){
		final String val = request.getParameter(ID_PARAM_NAME);
		if(StringUtils.isBlank(val)){
			return new AuditLogItemForm();
		}
		return new AuditLogItemForm( auditLogItemService.getById(Long.valueOf(val)) );
	}
	
	private void prepareModelFor(final long id, ModelMap modelMap, AuditLogItemForm form) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		model.put("auditLog", getAuditLog(id));
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
		if(!isLoggedUserAuthorizedTo(auditLog)){
			throw new AccessDeniedException(UserUtils.getLoggedUser() +  " tried to access " + auditLog);
		}
		return auditLog;
	}
	
	private boolean isLoggedUserAuthorizedTo(final AuditLog auditLog){
		final User user = UserUtils.getLoggedUser();
		if(user.isQuasarAdmin() || user.getId().equals(auditLog.getAuditor().getId())){
			return true;
		}
		return false;
	}
	
}
