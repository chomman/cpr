package cz.nlfnorm.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import cz.nlfnorm.constants.Constants;

public class CustomLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response ) {
		
		if(StringUtils.isNotBlank(request.getParameter(Constants.PORTAL_ID_PARAM_KEY))){
			return "/" + Constants.PORTAL_URL;
		}
		return Constants.ADMIN_ENTRY_POIN_REDIRECT_URL;
	}

	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		 super.handle(request, response, authentication);
	}
}
