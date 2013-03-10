package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.ExceptionLogDao;
import sk.peterjurkovic.cpr.entities.ExceptionLog;

@Repository("exceptionLogDao")
public class ExceptionLogDaoImpl extends BaseDaoImpl<ExceptionLog, Long> implements ExceptionLogDao{
	
	public ExceptionLogDaoImpl(){
		super(ExceptionLog.class);
	}
	
}
