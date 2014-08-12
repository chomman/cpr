package cz.nlfnorm.web.controllers.admin.cpr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.nlfnorm.dto.ReportDto;
import cz.nlfnorm.entities.Report;
import cz.nlfnorm.exceptions.ItemNotFoundException;
import cz.nlfnorm.services.ReportService;
import cz.nlfnorm.validators.admin.ReportValidator;
import cz.nlfnorm.web.controllers.admin.AdminSupportController;
import cz.nlfnorm.web.editors.LocalDateEditor;
import cz.nlfnorm.web.json.JsonResponse;
import cz.nlfnorm.web.json.JsonStatus;

@Controller
public class ReportController extends AdminSupportController {
	
	private final static int CPR_TAB_INDEX = 10;
	private final static String SUCCESS_DELETE_PARAM = "successDelete";
	
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
		if(StringUtils.isNotBlank(request.getParameter(SUCCESS_DELETE_PARAM))){
			map.put(SUCCESS_DELETE_PARAM, true);
		}
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
	
	@RequestMapping(value = "/admin/cpr/report/delete/{id}", method = RequestMethod.GET)
	public String removeReport(@PathVariable Long id, ModelMap map) throws ItemNotFoundException{
		final Report report = reportService.getById(id);
		if(report == null){
			throw new ItemNotFoundException(String.format("Report with [id=%s] was not found", id));
		}
		reportService.delete(report);
		return "redirect:/admin/cpr/reports?"+SUCCESS_DELETE_PARAM+"=1";
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
	
	
	@RequestMapping(value = "/admin/cpr/report/edit/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponse  processAjaxSubmit(@RequestBody Report form){
		JsonResponse response = new JsonResponse();
		List<String> errors = reportValidator.validate( form );	
		if(errors.size() > 0){
			response.setResult(errors);
		}else{
			try {
				executeUpdate(form);
				response.setStatus(JsonStatus.SUCCESS);
			} catch (ItemNotFoundException e) {
				logger.warn( e.getMessage() );
			}
		}
		return response;
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
		ReportDto dto = reportService.getItemsFor(report, false);
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
