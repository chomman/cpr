package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.DossierReportItemDao;
import cz.nlfnorm.quasar.entities.DossierReportItem;

@Repository("dossierReportItemDao")
public class DossierReportItemDaoImpl extends BaseDaoImpl<DossierReportItem, Long> implements DossierReportItemDao{
	
	public DossierReportItemDaoImpl(){
		super(DossierReportItem.class);
	}
}
