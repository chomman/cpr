package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorEacCode;

/**
 * 
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
public interface AuditorEacCodeService {
	
	void create(AuditorEacCode auditorEacCode);
	
	void update(AuditorEacCode auditorEacCode);
	
	void delete(AuditorEacCode auditorEacCode);
	
	AuditorEacCode getById(Long id);
	
	List<AuditorEacCode> getAllAuditorEacCodes(Auditor auditor);
		
	void syncAuditorEacCodes(Auditor auditor);
	
	AuditorEacCode getByEacCode(String code, Long auditorId);
}
