package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.Authority;


public interface AuthorityDao extends BaseDao<Authority, Long> {

    public Authority getAuthorityByName(String name);
}
