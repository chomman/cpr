package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.StandardGroup;

/**
 * Rozhranie datovej vrstvy entity sk.peterjurkovic.cpr.entities.StandardGroup
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface StandardGroupDao extends BaseDao<StandardGroup, Long>{

	Long getCoutOfStandardInGroup(StandardGroup standardGroup);
	
	boolean isGroupNameUniqe(String code, Long id);
}
