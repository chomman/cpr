package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.quasar.entities.NandoCode;

/**
 * QUASAR component
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public interface NandoCodeService{
	
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
}
