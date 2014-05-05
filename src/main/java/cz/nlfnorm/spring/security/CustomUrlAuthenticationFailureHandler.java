package cz.nlfnorm.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import cz.nlfnorm.constants.Constants;



/**
 * Implementacia posluchaca chybnych prihlaseni. Po neuspesnom prihlaseni presmeruje na definovanu url.
 * 
 * @author peto
 *
 */
public class CustomUrlAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	protected Logger logger = Logger.getLogger(getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
	
		redirectStrategy.sendRedirect(request, response, getUrl(request, ex));
		

	}
	
	
	protected String getUrl(HttpServletRequest request, AuthenticationException failed) {
		if(StringUtils.isNotBlank(request.getParameter(Constants.PORTAL_ID_PARAM_KEY))){
			return Constants.PORTAL_FAILURE_LOGIN_URL;
		}
		return Constants.ADMIN_FAILURE_LOGIN_URL;
    }
	
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
