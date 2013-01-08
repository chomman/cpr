package sk.peterjurkovic.cpr.dao;

import java.util.List;

import org.joda.time.DateTime;

import sk.peterjurkovic.cpr.entities.Standard;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.Standard
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardDao extends BaseDao<Standard, Long> {
	
	
	 List<Standard> getStandardPage(int pageNumber,Long standardGroupId, int orderById,
			String query, DateTime startValidity, DateTime stopValidity);
	
	 
	 
	Long getCountOfSdandards(Long standardGroupId,int orderById, String query, DateTime startValidity, DateTime stopValidity);
	
	
	
	
	boolean isStandardIdUnique(String standardId, Long id);
}
