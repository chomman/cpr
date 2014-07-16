package cz.nlfnorm.quasar.dao;

import java.util.Map;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.quasar.entities.DocumentationLog;

public interface DocumentationLogDao extends BaseDao<DocumentationLog, Long>{
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
}
