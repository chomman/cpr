package sk.peterjurkovic.cpr.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.utils.RequestUtils;




public class CustomUrlAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	protected Logger logger = Logger.getLogger(getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
	
		redirectStrategy.sendRedirect(request, response, gatUrl(request, ex));
		

	}
	
	
	protected String gatUrl(HttpServletRequest request, AuthenticationException failed) {
		
		String prefix = RequestUtils.getPartOfURLOnPosition(request, 1);
		
		logger.info("Prefix: " + prefix );
		
		if(prefix.equals(Constants.ADMIN_PREFIX)){
			return Constants.FAILURE_ROLE_ADMIN_URL;
		}
		
		return Constants.FAILURE_ROLE_USER_URL;
    }
	
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
