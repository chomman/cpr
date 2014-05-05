package cz.nlfnorm.dao;

import java.util.List;

import cz.nlfnorm.entities.Report;

public interface ReportDao extends BaseDao<Report, Long>{
	
	List<Report> getReports(boolean enabledOnly);
}
