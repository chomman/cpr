package sk.peterjurkovic.cpr.listeners;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import sk.peterjurkovic.cpr.entities.User;


public class LogoutListener implements LogoutHandler, ApplicationListener {

    protected final Log logger = LogFactory.getLog(getClass());


    public void onApplicationEvent(ApplicationEvent evt) {
        if (evt instanceof HttpSessionDestroyedEvent) {
            HttpSessionDestroyedEvent httpSessionDestroyedEvent = (HttpSessionDestroyedEvent)evt;
            logger.debug("Session byla smazana");
            HttpSession session = httpSessionDestroyedEvent.getSession();
            Object contextFromSessionObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            if (contextFromSessionObject != null) {
                if (contextFromSessionObject instanceof SecurityContext) {
                    SecurityContext context = (SecurityContext)contextFromSessionObject;
                    logout(context.getAuthentication(), session);
                }
            }
        }
    }


    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logout(authentication, request.getSession());

    }

    public void logout(Authentication authentication, HttpSession session) {
        if (authentication != null && !AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            User user = (User)authentication.getPrincipal();
            String sessionId = null;
            if (authentication.getDetails() != null) {
                sessionId = ((WebAuthenticationDetails)authentication.getDetails()).getSessionId();
            }
          //  userLogManager.logout(new Date().getTime(), sessionId);
            logger.info("Uzivatel byl odhlasen. Username: " + user.getUsername());
        }
    }
}