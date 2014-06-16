package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.quasar.entities.Auditor;

public interface AuditorService {
	
	void create(Auditor auditor);
	
	void update(Auditor auditor);
	
	void delete(Auditor auditor);
	
	Auditor getById(Long id);
	
	List<Auditor> getAll();
	
	void createOrUpdate(Auditor auditor);
	
	boolean isItcIdUniqe(Integer id, Long auditorId);
	
	Long createAuditor(Auditor auditor, String password);
	
	List<AutocompleteDto> autocomplete(String term, Boolean enabledObly, Boolean adminsOnly);
}
