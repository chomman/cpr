package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.StandardGroupDao;
import sk.peterjurkovic.cpr.entities.StandardGroup;

@Repository("standardGroupDao")
public class StandardGroupDaoImpl extends BaseDaoImpl<StandardGroup, Long> implements StandardGroupDao {
	
	public StandardGroupDaoImpl(){
		super(StandardGroup.class);
	}
	
}
