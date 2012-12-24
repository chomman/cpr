package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;


import sk.peterjurkovic.cpr.dao.AuthorityDao;
import sk.peterjurkovic.cpr.entities.Authority;

@Repository("authorityDao")
public class AuthorityDaoImpl extends BaseDaoImpl<Authority, Long> implements AuthorityDao {
	
	 /**
     * @param persistentClass
     */
    public AuthorityDaoImpl() {
        super(Authority.class);
    }

    public Authority getAuthorityByName(String name) {
       return (Authority)sessionFactory.getCurrentSession()
        	.createQuery("FROM " + persistentClass.getName() + " WHERE name=:name")
        	.setString("name", name)
        	.setMaxResults(1)
        	.uniqueResult();
    	
    }
}
