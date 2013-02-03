package sk.peterjurkovic.cpr.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
		
	protected Logger logger = Logger.getLogger(getClass());
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("has occured in the application ex: " + ex.getMessage());
		ex.printStackTrace();
		return super.doResolveException(request, response, handler, ex);
	}
}
