package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.AuthorityDao;
import cz.nlfnorm.entities.Authority;

/**
 * Trieda implementujuca rezhranie AuthorityDao.
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("authorityDao")
public class AuthorityDaoImpl extends BaseDaoImpl<Authority, Long> implements AuthorityDao {
	
	 /**
     * @param persistentClass
     */
    public AuthorityDaoImpl() {
        super(Authority.class);
    }
    
    
    /**
     * Metoda vrati rolu na zakladne nazvu
     * 
     * @return Atuhority uzivatelska rola, v pripade ak neexistuje je navratova hodnota NULL
     */
    public Authority getAuthorityByName(String name) {
       return (Authority)sessionFactory.getCurrentSession()
        	.createQuery("FROM " + persistentClass.getName() + " WHERE name=:name")
        	.setString("name", name)
        	.setMaxResults(1)
        	.uniqueResult();
    	
    }
}
