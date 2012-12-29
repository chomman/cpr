package sk.peterjurkovic.cpr.listeners;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.UserLogService;


public class LoginListener implements ApplicationListener<ApplicationEvent> {

    protected Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private UserLogService userLogService;
    
    @Override
    public void onApplicationEvent(ApplicationEvent evt) {
        if (evt instanceof InteractiveAuthenticationSuccessEvent) {
            InteractiveAuthenticationSuccessEvent authenticationSuccessEvent = (InteractiveAuthenticationSuccessEvent)evt;
            User user = (User)authenticationSuccessEvent.getAuthentication().getPrincipal();
            String ipAddress = ((WebAuthenticationDetails)authenticationSuccessEvent.getAuthentication().getDetails()).getRemoteAddress();
            String sessionId = ((WebAuthenticationDetails)authenticationSuccessEvent.getAuthentication().getDetails()).getSessionId();
            userLogService.saveSuccessLogin(user, ipAddress, authenticationSuccessEvent.getTimestamp(), sessionId);
            logger.info("Uzivatel '" + user.getUsername() + "' byl prihlasen, zdroj udalosti: " + authenticationSuccessEvent.getSource());
        } else if (evt instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent)evt;
            String username = (String)event.getAuthentication().getPrincipal();
            //String ipAddress = ((WebAuthenticationDetails)event.getAuthentication().getDetails()).getRemoteAddress();
            logger.info("Uzivatel '" + username + "' nebyl prihlasen: " + event.getException().getMessage());
        }
    }

}
