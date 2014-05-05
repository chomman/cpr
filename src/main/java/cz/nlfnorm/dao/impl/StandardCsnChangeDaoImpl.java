package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.StandardCsnChangeDao;
import cz.nlfnorm.entities.StandardCsnChange;

@Repository("standardCsnChangeDao")
public class StandardCsnChangeDaoImpl extends BaseDaoImpl<StandardCsnChange, Long> implements StandardCsnChangeDao{

	public StandardCsnChangeDaoImpl(){
		super(StandardCsnChange.class);
	}
}
