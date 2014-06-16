package cz.nlfnorm.quasar.dao;

import java.util.List;

import cz.nlfnorm.dao.BaseDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;


/**
 * DAO interface for NANDO code of QUASAR
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
public interface NandoCodeDao extends BaseDao<NandoCode, Long>{
	
	List<NandoCode> getCodesForAuditorType(int type, boolean onlyFirstLevel);
	
	List<NandoCode> getFirstLevelCodes();
	
	List<NandoCode> getSecondLevelCodes();
	
	List<NandoCode> getAllEnabled();
	
	NandoCode getByNandoCode(String code);
	
	int getNextOrderInNode(Long nodeId);
	
	List<NandoCode> getAllNonAssociatedAuditorsNandoCodes(Auditor auditor);
}
