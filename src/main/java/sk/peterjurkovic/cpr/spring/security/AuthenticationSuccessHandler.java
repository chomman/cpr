package sk.peterjurkovic.cpr.spring.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	

	
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String targetUrl = getTargetUrl(request, response, authentication);
        logger.debug("Redirecting to Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


	
	protected String getTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		

		User user = (User)authentication.getPrincipal();
        Set<Authority> authorities = user.getAuthoritySet();
        
        if(authorities.contains(Authority.ROLE_USER)){
        	return Constants.SUCCESS_ROLE_USER_URL;
        }else if(authorities.contains(Authority.ROLE_ADMIN)){
        	return Constants.SUCCESS_ROLE_ADMIN_URL;
        }
        
		return getDefaultTargetUrl();
	}

	
}
