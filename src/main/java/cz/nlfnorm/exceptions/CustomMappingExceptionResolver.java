package cz.nlfnorm.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.web.controllers.fontend.PortalWebpageController;


public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
		
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ExceptionLogService exceptionLogService;
	
	
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if(ex instanceof PortalAccessDeniedException){
			return new ModelAndView("redirect:" + PortalWebpageController.ACCESS_DENIED_URL);
		}
		if(!(ex instanceof PageNotFoundEception) && !(ex instanceof ItemNotFoundException)){
			logger.error(ExceptionUtils.getStackTrace(ex));
			exceptionLogService.logException(request, ex);
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return super.doResolveException(request, response, handler, ex);
	}
	
}
