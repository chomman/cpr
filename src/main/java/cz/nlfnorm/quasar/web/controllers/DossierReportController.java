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

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.enums.DossierReportCategory;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.CompanyService;
import cz.nlfnorm.quasar.services.DossierReportItemService;
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.quasar.web.forms.DossireReportItemForm;
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
	private DossierReportItemService dossierReportItemService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private NandoCodeService nandoCodeService;
	@Autowired
	private LocalDateEditor localDateEditor;
	
	public DossierReportController(){
		setTableItemsView("dossier-report-list");
		setEditFormView("dossier-report-edit");
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdentifiableByLongPropertyEditor<Company>( companyService ));
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	
	@RequestMapping(LIST_MAPPING_URL)
	public String handleProfileAuditLogs(ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> model = handlePageRequest(request, dossierReportService, LIST_MAPPING_URL);
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
		prepareModelFor(report, modelMap, getForm(request, report), isLogItemIdSet(request));
		return getEditFormView();
	}
	
	private void prepareModelFor(final DossierReport report, ModelMap modelMap,final DossireReportItemForm form, final boolean showForm) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		model.put("log", report);
		model.put("statusType", ChangeLogStatusController.ACTION_DOSSIER_REPORT);
		model.put("showForm", showForm);
		model.put("categories", DossierReportCategory.getAll());
		model.put("dateThreshold", dossierReportService.getEarliestPossibleDateForLog(report.getAuditor()));
		modelMap.addAttribute(COMMAND, form);
		appendModel(modelMap, model);
	}
	
	private DossierReport getDossierReportById(final long id) throws ItemNotFoundException{
		final DossierReport report = dossierReportService.getById(id);
		if(report == null){
			throw new ItemNotFoundException();
		}
		AccessUtils.validateAuthorizationFor(report);
		return report;
	}
	
	private DossireReportItemForm getForm(HttpServletRequest request, final DossierReport dossierReport){
		final Long id = getItemId(request);
		if(id == null || id == 0){
			return new DossireReportItemForm(dossierReport);
		}
		return new DossireReportItemForm(dossierReport, dossierReportItemService.getById( id ));
	}

}
