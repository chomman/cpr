package cz.nlfnorm.quasar.dao;

import java.util.List;
import java.util.Map;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.views.ProductAssessorA;
import cz.nlfnorm.quasar.views.ProductAssessorR;
import cz.nlfnorm.quasar.views.ProductSpecialist;
import cz.nlfnorm.quasar.views.QsAuditor;

/**
 * QUASAR Auditor DAO interface
 *  
 * @author Peter Jurkovic
 * @date Jun 12, 2014
 */
public interface AuditorDao extends BaseDao<Auditor, Long>{
	
	boolean isItcIdUniqe(Integer id, Long auditorId);
	
	List<AutocompleteDto> autocomplete(String term, Boolean enabledObly, Boolean adminsOnly);
	
	QsAuditor getQsAuditorById(Long id);
	
	ProductAssessorA getProductAssessorAById(Long id);
	
	ProductAssessorR getProductAssessorRById(Long id);
	
	ProductSpecialist getProductSpecialistById(Long id);
	
	PageDto getAuditorPage(int pageNumber, Map<String, Object> criteria);
	
	List<Auditor> getAuditors(Map<String, Object> criteria);
	
	Integer getCountOfAuditDaysInRecentYear(Long auditorId);
	
	Integer getCountOfTrainingHoursInRecentYear(Long auditorId);
	
	Integer getCountOfDesignDossiersInLastDays(Long auditorId, int countOfDays);
	
	Integer getCountOfTechnicalFilesInLastDays(Long auditorId, int countOfDays);
}
