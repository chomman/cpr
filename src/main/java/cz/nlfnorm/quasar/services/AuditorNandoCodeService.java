package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorNandoCode;

/**
 * QUASAR
 * 
 * @author Peter Jurkovic
 * @date Jun 16, 2014
 */
public interface AuditorNandoCodeService {

void create(AuditorNandoCode AuditorNandoCode);
	
	void update(AuditorNandoCode AuditorNandoCode);
	
	void delete(AuditorNandoCode AuditorNandoCode);
	
	AuditorNandoCode getById(Long id);
	
	List<AuditorNandoCode> getAllAuditorNandoCodes(Auditor auditor);
		
	void syncAuditorNandoCodes(Auditor auditor);
	
	AuditorNandoCode getByNandoCode(String code, Long auditorId);

	void createOrUpdate(final AuditorNandoCode auditorNandoCode);
	
	void syncNandoCodes();
	
	List<AuditorNandoCode> getForProductAssessorA(Auditor auditor);
	
	List<AuditorNandoCode> getForProductAssessorR(Auditor auditor);
	
	List<AuditorNandoCode> getForProductSpecialist(Auditor auditor);
	
	void incrementProductAssesorATotals(Long nandoCodeId, Long auditorId, int plusNbAudits, int plusIso13485Audits);
	
	void incrementProductAssessorRAndProductSpecialistTotals(Long nandoCodeId, Long auditorId, int plusTfReviews,int plusDdReviews);
}
