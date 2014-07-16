package cz.nlfnorm.quasar.dao;

import java.util.Map;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.PageDto;

public interface DocumentationLogDao extends BaseDao<DocumentationLogDao, Long>{
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
}
