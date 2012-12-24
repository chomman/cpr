package sk.peterjurkovic.cpr.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import sk.peterjurkovic.cpr.entities.User;


public class LoginListener implements ApplicationListener {

    protected final Log logger = LogFactory.getLog(getClass());

    //@Autowired
    //UserLogManager userLogManager;

    public void onApplicationEvent(ApplicationEvent evt) {
        if (evt instanceof InteractiveAuthenticationSuccessEvent) {
            InteractiveAuthenticationSuccessEvent authenticationSuccessEvent = (InteractiveAuthenticationSuccessEvent)evt;
            User user = (User)authenticationSuccessEvent.getAuthentication().getPrincipal();
            String ipAddress = ((WebAuthenticationDetails)authenticationSuccessEvent.getAuthentication().getDetails()).getRemoteAddress();
            String sessionId = ((WebAuthenticationDetails)authenticationSuccessEvent.getAuthentication().getDetails()).getSessionId();
           // userLogManager.succesLogin(user, ipAddress, authenticationSuccessEvent.getTimestamp(), sessionId);
            logger.info("Uzivatel '" + user.getUsername() + "' byl prihlasen, zdroj udalosti: " + authenticationSuccessEvent.getSource());
        } else if (evt instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent)evt;
            String username = (String)event.getAuthentication().getPrincipal();
            String ipAddress = ((WebAuthenticationDetails)event.getAuthentication().getDetails()).getRemoteAddress();
            logger.info("Uzivatel '" + username + "' nebyl prihlasen: " + event.getException().getMessage());
        }
    }

}
