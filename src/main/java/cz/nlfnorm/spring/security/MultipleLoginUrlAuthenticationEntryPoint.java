package cz.nlfnorm.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.utils.RequestUtils;

public class MultipleLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	
	public MultipleLoginUrlAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}
	
	@Override
	protected String determineUrlToUseForThisRequest(
			HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		
		if(StringUtils.isNotBlank(request.getParameter(Constants.PORTAL_ID_PARAM_KEY))){
			return "/" + Constants.PORTAL_URL;
		}
		
		if(RequestUtils.urlContains(Constants.PORTAL_URL, request)){
			return "/" + Constants.PORTAL_URL;
		}
		
		return Constants.ADMIN_ENTRY_POIN_REDIRECT_URL;
	}
}
