package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.StandardCsn;


/**
 * Rozhranie implementujuce datovu vrstvu entity sk.peterjurkovic.cpr.entities.StandardCsn
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardCsnDao extends BaseDao<StandardCsn, Long>{
	
	StandardCsn getByCatalogNo(String catalogNumber);
	
	List<StandardCsn> autocomplete(String term);
	
}
