package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Report;

public interface ReportDao extends BaseDao<Report, Long>{
	
	List<Report> getReports(boolean enabledOnly);
}
