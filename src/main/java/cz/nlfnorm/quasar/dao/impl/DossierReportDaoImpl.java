package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.dao.DossierReportDao;
import cz.nlfnorm.quasar.entities.DossierReport;

@Repository("dossierReportDao")
public class DossierReportDaoImpl extends AbstractLogDaoImpl<DossierReport> implements DossierReportDao{
	
	public DossierReportDaoImpl(){
		super(DossierReport.class);
	}

}
