package sk.peterjurkovic.cpr.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import sk.peterjurkovic.cpr.constants.Constants;



public class MultipleLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	
	protected Logger logger = Logger.getLogger(getClass());
	
	public MultipleLoginUrlAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}
	
	
	
	
	
	
	@Override
	protected String determineUrlToUseForThisRequest(
			HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		
		if(exception != null){
			logger.error(exception);
		}
		
		if(StringUtils.isNotBlank(request.getParameter(Constants.PORTAL_ID_PARAM_KEY))){
			return "/" + Constants.PORTAL_URL;
		}

		return Constants.ADMIN_ENTRY_POIN_REDIRECT_URL;
	}
}
