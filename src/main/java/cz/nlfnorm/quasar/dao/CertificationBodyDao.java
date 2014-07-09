package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.CertificationBody;


public interface CertificationBodyDao extends BaseDao<CertificationBody, Long>{
	
	List<CertificationBody> autocomplete(String term);
	
	List<CertificationBody> getAll();
	
	CertificationBody findByName(String name);
	
}
