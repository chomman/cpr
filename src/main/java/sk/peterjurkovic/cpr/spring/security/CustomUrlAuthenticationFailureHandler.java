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
	
	private boolean forward = false;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
		
		if(forward){
			logger.debug("Forwarding to " + gatUrl(request, ex));
			request.getRequestDispatcher(gatUrl(request, ex)).forward(request, response);
		}else{
			logger.debug("Redirecting to " + gatUrl(request, ex));
			redirectStrategy.sendRedirect(request, response, gatUrl(request, ex));
		}

	}
	
	
	protected String gatUrl(HttpServletRequest request, AuthenticationException failed) {
		
		String prefix = RequestUtils.getPartOfURLOnPosition(request, 1);
		
		logger.info("CustomUrlAuthenticationFailureHandler prefix: " + prefix);
		
		if(prefix.equals(Constants.ADMIN_PREFIX)){
			return Constants.FAILURE_ROLE_ADMIN_URL;
		}
		
		return Constants.FAILURE_ROLE_USER_URL;
    }
	
	public boolean isForwarded(){
		return forward;
	}
	
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
