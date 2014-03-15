package sk.peterjurkovic.cpr.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sk.peterjurkovic.cpr.dto.ReportDto;
import sk.peterjurkovic.cpr.entities.Report;
import sk.peterjurkovic.cpr.exceptions.PageNotFoundEception;
import sk.peterjurkovic.cpr.services.ReportService;

@Controller
public class PublicReportController extends PublicSupportController{
	
	
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping( { "/reporty" ,  EN_PREFIX + "/reporty" } )
	public String shorReports(ModelMap map) throws PageNotFoundEception{
		 Map<String, Object> model = new HashMap<String, Object>();
		 model.put("reports", reportService.getReportsForPublic());
		 model.put("webpage", getWebpage("/reporty"));
		 map.put("model", model);
		return "public/cpr/cpr-base";
	}
	
	
	@RequestMapping( { "/report/{id}" ,  EN_PREFIX + "/report/{id}" } )
	public String showReport(ModelMap map, @PathVariable Long id) throws PageNotFoundEception{
		 Report report = reportService.getById(id);
		 if(report == null || !report.isEnabled()){
			 throw new PageNotFoundEception();
		 }
		 ReportDto dto = reportService.getItemsFor(report);
		 Map<String, Object> model = new HashMap<String, Object>();
		 model.put("report", report);
		 model.put("parentWebpage", getWebpage("/reporty"));
		 model.put("standards", dto.getStandards() );
		 model.put("standardCsns", dto.getStandardCsns() );
		 map.put("model", model);
		return "public/report-detail";
	}
}
