package cz.nlfnorm.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.User;


/**
 * Implementacia posluchaca uspesnych prihlaseni. Po neuspesnom prihlaseni presmeruje na definovanu url.
 * 
 * @author peto
 *
 */
public class CustomUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	protected Logger logger = Logger.getLogger(getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		String targetUrl = getTargetUrl(request, response, authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


	
	protected String getTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		final User user = (User)authentication.getPrincipal();
		
		if(StringUtils.isNotBlank(request.getParameter(Constants.PORTAL_ID_PARAM_KEY))){
			return "/" + Constants.PORTAL_URL;
		}
		
		if(user.isAdministrator()){
			return Constants.SUCCESS_ROLE_ADMIN_URL;
		}
		
		return "/" + Constants.PORTAL_URL;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
}
