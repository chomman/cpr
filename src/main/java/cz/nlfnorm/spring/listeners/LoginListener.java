package cz.nlfnorm.spring.listeners;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.UserLogService;


public class LoginListener implements ApplicationListener<ApplicationEvent> {

    protected Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private UserLogService userLogService;
    
    @Override
    public void onApplicationEvent(ApplicationEvent evt) {
        if (evt instanceof InteractiveAuthenticationSuccessEvent) {
            InteractiveAuthenticationSuccessEvent authenticationSuccessEvent = (InteractiveAuthenticationSuccessEvent)evt;
            final User user = (User)authenticationSuccessEvent.getAuthentication().getPrincipal();
            final String ipAddress = ((WebAuthenticationDetails)authenticationSuccessEvent.getAuthentication().getDetails()).getRemoteAddress();
            final String sessionId = ((WebAuthenticationDetails)authenticationSuccessEvent.getAuthentication().getDetails()).getSessionId();
            userLogService.saveSuccessLogin(user, ipAddress, authenticationSuccessEvent.getTimestamp(), sessionId);
        } else if (evt instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent)evt;
            final String username = (String)event.getAuthentication().getPrincipal();
            final String ipAddress = ((WebAuthenticationDetails)event.getAuthentication().getDetails()).getRemoteAddress();
            userLogService.saveFailureLogin(username, ipAddress, event.getTimestamp());
        }
    }

}
