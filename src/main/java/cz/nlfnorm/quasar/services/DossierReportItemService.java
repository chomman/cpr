package cz.nlfnorm.quasar.services;

import cz.nlfnorm.quasar.entities.DossierReportItem;

public interface DossierReportItemService {

	void create(DossierReportItem dossierReportItem);
	
	void update(DossierReportItem dossierReportItem);
	
	void delete(DossierReportItem dossierReportItem);
	
	DossierReportItem getById(long id);
	
	void createOrUpdate(DossierReportItem dossierReportItem);
}
