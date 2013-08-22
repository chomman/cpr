package sk.peterjurkovic.cpr.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import sk.peterjurkovic.cpr.services.ExceptionLogService;


public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
		
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ExceptionLogService exceptionLogService;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		logger.info("V aplikacii nastala chyba: " + ex.getMessage());
		exceptionLogService.logException(request, ex);
		return super.doResolveException(request, response, handler, ex);
	}
}
