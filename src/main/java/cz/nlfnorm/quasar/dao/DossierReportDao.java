package cz.nlfnorm.quasar.dao;

import java.util.Map;

import org.joda.time.LocalDate;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.DossierReport;

public interface DossierReportDao extends BaseDao<DossierReport, Long>{
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
	LocalDate getEarliestPossibleDateForLog(Long auditorId);
}
