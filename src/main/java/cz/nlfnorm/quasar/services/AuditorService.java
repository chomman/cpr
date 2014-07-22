package cz.nlfnorm.quasar.services;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorFunctions;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorNandoCode;
import cz.nlfnorm.quasar.dto.EvaluatedAuditorNandoFunctionDto;
import cz.nlfnorm.quasar.dto.EvaluatedEacCode;
import cz.nlfnorm.quasar.dto.EvaludatedQsAuditorDto;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.AuditorExperience;
import cz.nlfnorm.quasar.entities.SpecialTraining;
import cz.nlfnorm.quasar.views.AbstractNandoFunction;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
import cz.nlfnorm.quasar.views.ProductSpecialist;
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
	
	List<EvaluatedEacCode> evaluateCodesForQsAuditor(Auditor auditor);
	
	ProductAssessorA getProductAssessorAById(Long id);
	
	ProductAssessorR getProductAssessorRById(Long id);
	
	ProductSpecialist getProductSpecialistById(Long id);
	
	List<EvaludatedQsAuditorDto> evaluateForQsAuditor(List<Auditor> auditorList);
	
	List<EvaluatedAuditorNandoFunctionDto> evaludateForProductAssessorA(List<Auditor> auditorList);
	
	List<EvaluatedAuditorNandoFunctionDto> evaludateForProductAssessorR(List<Auditor> auditorList);
	
	List<EvaluatedAuditorNandoFunctionDto> evaludateForProductSpecialist(List<Auditor> auditorList);
	
	List<EvaluatedAuditorNandoCode> evaluateAuditorNandoCodesFor(AbstractNandoFunction function, Auditor auditor);
	
	PageDto getAuditorPage(int pageNumber, Map<String, Object> criteria);
	
	List<Auditor> getAuditors(Map<String, Object> criteria);
	
	EvaluatedAuditorFunctions getEvaludatedAuditorFunctions(Auditor auditor);
	
	List<EvaluatedAuditorFunctions> getEvaludatedAuditorFunctions(List<Auditor> auditor);
	
	Integer getCountOfAuditDaysInRecentYear(final Long auditorId);
	
}
