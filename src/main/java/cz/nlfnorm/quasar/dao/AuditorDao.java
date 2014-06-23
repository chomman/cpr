package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.dto.AutocompleteDto;
import cz.nlfnorm.quasar.entities.Auditor;
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
}
