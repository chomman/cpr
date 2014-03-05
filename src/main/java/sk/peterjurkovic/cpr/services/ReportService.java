package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Report;

public interface ReportService {
	
	void create(Report report);
	
	void delete(Report report);
	
	void update(Report report);
	
	Report getById(Long id);
	
	List<Report> getAll();
	
	void createOrUpdate(Report report);
	
}
