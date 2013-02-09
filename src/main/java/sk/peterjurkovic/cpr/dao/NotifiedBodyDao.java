package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.NotifiedBody;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.NotifiedBody
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface NotifiedBodyDao extends BaseDao<NotifiedBody, Long> {
	
	boolean canBeDeleted(NotifiedBody notifiedBody);
	
	boolean isNameUniqe(String code, Long id); 
	
	List<NotifiedBody> getNotifiedBodiesGroupedByCountry(Boolean enabled);
}
