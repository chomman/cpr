package cz.nlfnorm.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.services.ExceptionLogService;
import cz.nlfnorm.web.controllers.fontend.PortalWebpageController;


public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
		
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ExceptionLogService exceptionLogService;
	@Autowired
	private NlfnormMailSender nlfnormMailSender;
	
	@Value("#{config['mail.sendExcaptions']}")
	private boolean sendExceptions;
	
	@Value("#{config['mail.sendExceptionTo']}")
	private String sendExceptionsToEmailAddress;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if(ex instanceof PortalAccessDeniedException){
			logger.error(ex);
			return new ModelAndView("redirect:" + PortalWebpageController.ACCESS_DENIED_URL);
		}
		if(!(ex instanceof PageNotFoundEception) && !(ex instanceof ItemNotFoundException)){
			logger.error(ex);
			exceptionLogService.logException(request, ex);
			if(sendExceptions){
				sendExceptionAlertEmail(ex);
			}
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return super.doResolveException(request, response, handler, ex);
	}
	
	
	private void sendExceptionAlertEmail(final Exception ex){
		Thread thread = new Thread(){
			public void run() {
				HtmlMailMessage message = new HtmlMailMessage("portal@nlfnorm.cz", ex.getClass().getName());
				message.addRecipientTo(sendExceptionsToEmailAddress);
				message.setHtmlContent(ex.getStackTrace().toString());
				nlfnormMailSender.send(message);
			}
		};
		thread.run();
	}
}
