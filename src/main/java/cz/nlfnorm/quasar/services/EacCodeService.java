package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.EacCode;

/**
 * QUASAR component
 * 
 * Service, which serves business logic of EAC codes
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public interface EacCodeService {
	
	EacCode getById(Long id);
	
	EacCode getByCode(String code);
	
	List<EacCode> getAll();
	
	List<EacCode> getAllForQsAuditor();
	
	void create(EacCode eacCode);
	
	void update(EacCode eacCode);
	
	void delete(EacCode eacCode);
	
	void createOrUpdate(EacCode eacCode);
	
	List<EacCode> getAllNonAssociatedAuditorsEacCodes(Auditor auditor);
}
