package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.services.IdentifiableByLongService;

/**
 * QUASAR component
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public interface NandoCodeService extends IdentifiableByLongService<NandoCode>{
	
	NandoCode getById(Long id);
	
	NandoCode getByNandoCode(String code);
	
	List<NandoCode> getAll();
	
	void delete(NandoCode nandoCode);
	
	void update(NandoCode nandoCode);
	
	void create(NandoCode nandoCode);
	
	List<NandoCode> getFirstLevelCodes();
	
	List<NandoCode> getSecondLevelCodes();
	
	List<NandoCode> getForProductAssessorA();
	
	List<NandoCode> getForProductAssessorR();
	
	List<NandoCode> getForProductSpecialist();
	
	void createOrUpdate(NandoCode nandoCode);
	
	List<NandoCode> getAllNonAssociatedAuditorsNandoCodes(Auditor auditor);
	
	List<NandoCode> getCodesForProductAssesorOrSpecialist(boolean enabledOnly);
}
