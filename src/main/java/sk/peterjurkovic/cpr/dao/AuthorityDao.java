package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.Authority;


public interface AuthorityDao extends BaseDao<Authority, Long> {
	
	/**
	 * Vrati uzivatelsku rolu, na zaklade danoho nazvu
	 * 
	 * @param String name
	 * @return Authority
	 */
    public Authority getAuthorityByName(String name);
}
