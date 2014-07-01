package cz.nlfnorm.quasar.services;

import java.util.List;

import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorNandoCode;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorExperience;
import cz.nlfnorm.quasar.entities.SpecialTraining;
import cz.nlfnorm.quasar.views.AbstractNandoFunction;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
import cz.nlfnorm.quasar.views.QsAuditor;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface AuditorService extends IdentifiableByLongService<Auditor>{
	
	final static int PERSONAL_DATA_ACTION = 1;
	
	void create(Auditor auditor);
	
	void update(Auditor auditor);
	
	void delete(Auditor auditor);
	
	Auditor getById(Long id);
	
	List<Auditor> getAll();
	
	void createOrUpdate(Auditor auditor);
	
	boolean isItcIdUniqe(Integer id, Long auditorId);
	
	Long createAuditor(Auditor auditor, String password);
	
	List<AutocompleteDto> autocomplete(String term, Boolean enabledObly, Boolean adminsOnly);
	
	void update(Auditor auditor, int action);
	
	void createOrUpdateAuditorExperience(AuditorExperience auditorExperience);
	
	void createAuditorSpecialTraining(SpecialTraining form);
	
	void removeAuditorSpecailTraining(Auditor auditor, Long specialTrainingId);
	
	QsAuditor getQsAuditorById(Long id);
	
	ProductAssessorA getProductAssessorAById(Long id);
	
	ProductAssessorR getProductAssessorRById(Long id);
	
	List<EvaluatedAuditorNandoCode> evaluate(AbstractNandoFunction function, Auditor auditor);
}
