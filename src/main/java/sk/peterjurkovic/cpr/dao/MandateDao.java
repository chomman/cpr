package sk.peterjurkovic.cpr.dao;

import java.util.List;

import sk.peterjurkovic.cpr.entities.Mandate;


/**
 * Rozhranie datevej vrstvy entity sk.peterjurkovic.cpr.entities.Mandate
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface MandateDao extends BaseDao<Mandate, Long> {
	
	List<Mandate> getMandatePage(int pageNumber);
	
	Long getCountOfMandates();
	
	boolean canBeDeleted(final Mandate mandate);
}
