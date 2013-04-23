package sk.peterjurkovic.cpr.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.User;



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
		User user = (User)authentication.getPrincipal();
        /*
		if(user.isEditorUser()){
        	return Constants.SUCCESS_ROLE_ADMIN_URL;
        }
        */
		return Constants.SUCCESS_ROLE_ADMIN_URL;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
}
