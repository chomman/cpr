package sk.peterjurkovic.cpr.services;

import javax.servlet.http.HttpServletRequest;

import sk.peterjurkovic.cpr.entities.ExceptionLog;

public interface ExceptionLogService {
	
	void create(ExceptionLog exceptionLog);
	
	void logException(HttpServletRequest request, Exception exception);
	
}
