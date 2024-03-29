package cz.nlfnorm.dao;

import cz.nlfnorm.entities.Authority;

/**
 * DAO rozhranie pre manipulaciu s uzivatelskymi rolami
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
public interface AuthorityDao extends BaseDao<Authority, Long> {
	
	/**
	 * Vrati uzivatelsku rolu, na zaklade danoho nazvu
	 * 
	 * @param String name
	 * @return Authority
	 */
    public Authority getAuthorityByName(String name);
}
