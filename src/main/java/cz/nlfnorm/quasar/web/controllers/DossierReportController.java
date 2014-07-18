package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.enums.LogStatus;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.CompanyService;
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.utils.RequestUtils;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;
import cz.nlfnorm.web.editors.LocalDateEditor;

@Controller
public class DossierReportController extends LogControllerSupport {
	
	private final static int TAB = 10;
	private final static String LIST_MAPPING_URL = "/admin/quasar/dossier-reports";
	private final static String EDIT_MAPPING_URL = "/admin/quasar/dossier-report/{id}";
	
	@Autowired
	private DossierReportService dossierReportService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private NandoCodeService nandoCodeService;
	@Autowired
	private LocalDateEditor localDateEditor;
	
	public DossierReportController(){
		setTableItemsView("documentation-log-list");
		setEditFormView("documentation-log-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdentifiableByLongPropertyEditor<Company>( companyService ));
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleProfileAuditLogs(ModelMap modelMap, HttpServletRequest request) {
		final Map<String, Object> criteria = RequestUtils.getRequestParameterMap(request);
		final boolean isQuasarAdmin = UserUtils.getLoggedUser().isQuasarAdmin();
		if(!isQuasarAdmin){
			criteria.put(AuditorFilter.AUDITOR, UserUtils.getLoggedUser().getId());
		}
		Map<String, Object> model = new HashMap<>();
		final int currentPage = RequestUtils.getPageNumber(request);
		final PageDto page = dossierReportService.getPage(criteria, currentPage);
		if(page.getCount() > 0){
			model.put("paginationLinks", getPaginationItems(request, criteria, page.getCount(), "/admin/quasar/manage/auditors"));
			model.put("logs", page.getItems());
		}
		model.put("statuses", LogStatus.getAll());
		model.put("params", criteria);
		model.put("isQuasarAdmin", isQuasarAdmin);
		model.put("partners", partnerService.getAll());
		appendTabNo(model, TAB);
		appendModel(modelMap, model);
		return getTableItemsView();
	}
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.GET)
	public String handleAuditLogEdit(ModelMap modelMap, @PathVariable long id, HttpServletRequest request) throws ItemNotFoundException{
		if(id == 0){
			id = dossierReportService.createNewToLoginedUser();
			return successUpdateRedirect(EDIT_MAPPING_URL.replace("{id}", id+""));
		}
		if(isDeleted(request)){
			appendSuccessCreateParam(modelMap);
		}
		if(isSucceded(request)){
			appendSuccessCreateParam(modelMap);
		}
		final DossierReport report = getDossierReportById(id);
	//	prepareModelFor(report, modelMap, getItem(request, report), isAuditLogItemIdSet(request));
		return getViewDir() + "profile/auditor-audit-log-edit";
	}
	
	
	private DossierReport getDossierReportById(final long id) throws ItemNotFoundException{
		final DossierReport report = dossierReportService.getById(id);
		if(report == null){
			throw new ItemNotFoundException();
		}
		AccessUtils.validateAuthorizationFor(report);
		return report;
	}

}
