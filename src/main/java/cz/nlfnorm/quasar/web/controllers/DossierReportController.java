package cz.nlfnorm.quasar.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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

import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.entities.DossierReportItem;
import cz.nlfnorm.quasar.enums.CertificationSuffix;
import cz.nlfnorm.quasar.enums.DossierReportCategory;
import cz.nlfnorm.quasar.security.AccessUtils;
import cz.nlfnorm.quasar.services.DossierReportItemService;
import cz.nlfnorm.quasar.services.DossierReportService;
import cz.nlfnorm.quasar.web.forms.DossierReportItemForm;
import cz.nlfnorm.quasar.web.validators.DossierReportItemValidator;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.web.editors.IdentifiableByLongPropertyEditor;

@Controller
public class DossierReportController extends LogControllerSupport {
	
	private final static String WORKER_ID_PARAM = "aid";
	private final static int TAB = 10;
	private final static String LIST_MAPPING_URL = "/admin/quasar/dossier-reports";
	public final static String EDIT_MAPPING_URL = "/admin/quasar/dossier-report/{id}";
	private final static String DELETE_MAPPING_URL = "/admin/quasar/dossier-report-item/delete/{id}";
	
	@Autowired
	private DossierReportService dossierReportService;
	@Autowired
	private DossierReportItemService dossierReportItemService;
	@Autowired
	private DossierReportItemValidator dossierReportItemValidator;
	
	
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
			String userId = request.getParameter(WORKER_ID_PARAM);
			if(StringUtils.isBlank(userId)){
				id = dossierReportService.createNewToLoginedUser();
			}else{
				id = dossierReportService.createNew(Long.valueOf(userId));
			}
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
	
	
	@RequestMapping(value = EDIT_MAPPING_URL, method = RequestMethod.POST)
	public String processSubmitAuditLogItem(
			ModelMap modelMap, 
			@Valid @ModelAttribute(COMMAND) DossierReportItemForm form,
			BindingResult result,
			@PathVariable long id, 
			HttpServletRequest request) throws ItemNotFoundException{
		final DossierReport	 dossierReport = getDossierReportById(id);
		boolean showForm = true;
		boolean hasErrors = result.hasErrors();
		if(!hasErrors){
			dossierReportItemValidator.validate(form, result);
			hasErrors = result.hasErrors();
			if(!hasErrors){
				createOrUpdate(dossierReport, form, modelMap);
				appendSuccessCreateParam(modelMap);
				showForm = false;
				form = getForm(request, dossierReport);
			}
		}
		if(hasErrors){
			setNandoCodes(form, form.getItem());
		}
		prepareModelFor(dossierReport, modelMap, form, showForm);
		return getEditFormView();
	}
	
	@RequestMapping(value = DELETE_MAPPING_URL)
	public String handleAuditLogEdit(final @PathVariable long id) throws ItemNotFoundException{
		final DossierReportItem	item = getDossierReportItem(id);
		final Long reportId = item.getDossierReport().getId();
		dossierReportService.updateAndSetChanged(item.getDossierReport());
		dossierReportItemService.delete(item);
		return successDeleteRedirect(EDIT_MAPPING_URL.replace("{id}", reportId+""));
	}
	
	
	private void createOrUpdate(final DossierReport	report, final DossierReportItemForm form, ModelMap modelMap) throws ItemNotFoundException{
		if(!report.isEditable()){
			throw new AccessDeniedException("Auditlog is not editable." + report + UserUtils.getLoggedUser());
		}
		DossierReportItem reportItem = null; 
		if(form.getItem().getId() == null){
			reportItem = form.getItem();
			reportItem.setDossierReport(report);
		}else{
			reportItem = getDossierReportItem(form.getItem().getId());
		}
		if(setAndCreateCompany(form, reportItem)){
			// Some company with given name was found and used.
			setCompanyFoundAlertMessage(modelMap, form.getCompanyName(), reportItem.getCompany().getName());
		}
		setNandoCodes(form, reportItem);
		reportItem.setAuditDate(form.getItem().getAuditDate());
		reportItem.setCertifiedProduct(form.getItem().getCertifiedProduct());
		reportItem.setCategory(form.getItem().getCategory());
		reportItem.setCertificationNo(form.getItem().getCertificationNo().replaceAll("\\s+",""));
		reportItem.setCertificationSufix(form.getItem().getCertificationSufix().toUpperCase());
		dossierReportItemService.createOrUpdate(reportItem);		
	}
	
	private void prepareModelFor(final DossierReport report, ModelMap modelMap,final DossierReportItemForm form, final boolean showForm) throws ItemNotFoundException{
		final Map<String, Object> model = new HashMap<>();
		model.put("log", report);
		model.put("statusType", ChangeLogStatusController.ACTION_DOSSIER_REPORT);
		model.put("showForm", showForm);
		model.put("categories", DossierReportCategory.getAll());
		model.put("suffixies", CertificationSuffix.getAll());
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
	
	private DossierReportItemForm getForm(HttpServletRequest request, final DossierReport dossierReport){
		final Long id = getItemId(request);
		if(id == null || id == 0){
			return new DossierReportItemForm(dossierReport);
		}
		return new DossierReportItemForm(dossierReport, dossierReportItemService.getById( id ));
	}
	
	private DossierReportItem getDossierReportItem(final long id) throws ItemNotFoundException{
		final DossierReportItem item  = dossierReportItemService.getById(id);
		if(item == null){
			throw new ItemNotFoundException("AuditLogItem [id="+id+"] not found");
		}
		AccessUtils.validateAuthorizationFor(item.getDossierReport());
		return item;
	}
	
}
