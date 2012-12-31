package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.NotifiedBodyDao;
import sk.peterjurkovic.cpr.entities.NotifiedBody;


@Repository("notifiedBody")
public class NotifiedBodyDaoImpl extends BaseDaoImpl<NotifiedBody, Long> implements NotifiedBodyDao {
	
	public NotifiedBodyDaoImpl(){
		super(NotifiedBody.class);
	}
	
}
