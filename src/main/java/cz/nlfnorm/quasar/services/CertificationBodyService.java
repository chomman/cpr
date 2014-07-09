package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.CertificationBody;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface CertificationBodyService extends IdentifiableByLongService<CertificationBody>{
	
	List<CertificationBody> autocomplete(String term);
	
	List<CertificationBody> getAll();
	
	void create(CertificationBody certificationBody);
	
	CertificationBody findByName(String name);
}
