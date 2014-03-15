package sk.peterjurkovic.cpr.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sk.peterjurkovic.cpr.dto.ReportDto;
import sk.peterjurkovic.cpr.entities.Report;
import sk.peterjurkovic.cpr.exceptions.ItemNotFoundException;
import sk.peterjurkovic.cpr.services.ReportService;
import sk.peterjurkovic.cpr.validators.admin.ReportValidator;
import sk.peterjurkovic.cpr.web.controllers.admin.SupportAdminController;
import sk.peterjurkovic.cpr.web.editors.LocalDateEditor;

@Controller
public class ReportController extends SupportAdminController {
	
	private final static int CPR_TAB_INDEX = 10;
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private LocalDateEditor localDateEditor;
	@Autowired
	private ReportValidator reportValidator;
	
	
	public ReportController() {
		setTableItemsView("cpr/reports");
		setEditFormView("cpr/report-edit");
		setViewName("cpr/report-add");
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, this.localDateEditor);
	}
	
	
	
	@RequestMapping(value = "/admin/cpr/reports")
	public String showReports(HttpServletRequest request, ModelMap map){
		Map<String, Object> model = prepareBaseModel();
		model.put("reports", reportService.getAll());
		map.put("model", model);
		return getTableItemsView();
	}
	
	
	@RequestMapping(value = "/admin/cpr/report/add", method = RequestMethod.GET)
	public String showAddForm(ModelMap map){
		appendModel(map, new Report());
		return getViewName();
	}
	
	
	@RequestMapping(value = "/admin/cpr/report/add", method = RequestMethod.POST)
	public String processCreateReport(Report report, BindingResult result, ModelMap map){
		reportValidator.validate(result, report);
		if(result.hasErrors()){
			appendModel(map, report);
			return getViewName();
		}
		reportService.create(report);
		return "redirect:/admin/cpr/report/edit/" + report.getId();
	}
	
	
	@RequestMapping(value = "/admin/cpr/report/edit/{id}", method = RequestMethod.GET)
	public String showEditForm(@PathVariable Long id, ModelMap map) throws ItemNotFoundException{
		final Report report = reportService.getById(id);
		if(report == null){
			throw new ItemNotFoundException(String.format("Report with [id=%s] was not found", id));
		}
		appendChangedStandards(map, report);
		return getEditFormView();
	}
	
	
	@RequestMapping(value = "/admin/cpr/report/edit/{id}", method = RequestMethod.POST)
	public String processEditReport(Report report, BindingResult result, ModelMap map) throws ItemNotFoundException{
		reportValidator.validate(result, report);
		if(result.hasErrors()){
			appendChangedStandards(map, report);
			return getEditFormView();
		}
		executeUpdate(report);
		map.put("successCreate", true);
		appendChangedStandards(map, report);
		return getEditFormView();
	}
	
	
	private void executeUpdate(Report form) throws ItemNotFoundException{
		Validate.notNull(form);
		Report persistedReport = reportService.getById(form.getId());
		if(persistedReport == null){
			throw new ItemNotFoundException(String.format("Report with [id=%s] was not found", form.getId()));
		}
		persistedReport.setEnabled(form.getEnabled());
		persistedReport.setContentCzech(form.getContentCzech());
		persistedReport.setContentEnglish(form.getContentEnglish());
		persistedReport.setDateFrom(form.getDateFrom());
		persistedReport.setDateTo(form.getDateTo());
		reportService.createOrUpdate(persistedReport);
	}
	
	@SuppressWarnings("unchecked")
	private void appendChangedStandards(ModelMap map, Report report){
		appendModel( map, report);
		ReportDto dto = reportService.getItemsFor(report);
		((Map<String, Object>)map.get("model")).put("standards", dto.getStandards() );
		((Map<String, Object>)map.get("model")).put("standardCsns", dto.getStandardCsns() );
	}
	
	private void appendModel(ModelMap map, Report report){
		map.put("model", prepareBaseModel());
		map.addAttribute("report", report);
	}
	
	
	private Map<String, Object> prepareBaseModel(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("tab", CPR_TAB_INDEX);
		return model;
	}
}
