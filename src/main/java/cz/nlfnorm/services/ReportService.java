package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.dto.ReportDto;
import cz.nlfnorm.entities.Report;

public interface ReportService {
	
	void create(Report report);
	
	void delete(Report report);
	
	void update(Report report);
	
	Report getById(Long id);
	
	List<Report> getAll();
	
	void createOrUpdate(Report report);
	
	ReportDto getItemsFor(Report report, boolean enabledOnly);
	
	List<Report> getReportsForPublic();
	
}
