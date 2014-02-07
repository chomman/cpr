package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.StandardCsnChangeDao;
import sk.peterjurkovic.cpr.entities.StandardCsnChange;

@Repository("standardCsnChangeDao")
public class StandardCsnChangeDaoImpl extends BaseDaoImpl<StandardCsnChange, Long> implements StandardCsnChangeDao{

	public StandardCsnChangeDaoImpl(){
		super(StandardCsnChange.class);
	}
}
