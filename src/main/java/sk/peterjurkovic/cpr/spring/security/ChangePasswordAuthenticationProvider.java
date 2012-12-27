package sk.peterjurkovic.cpr.spring.security;

import org.joda.time.DateTime;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import sk.peterjurkovic.cpr.entities.User;

public class ChangePasswordAuthenticationProvider implements AuthenticationProvider{

	private int changePasswordTokenValidity;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException   {
		 User user = (User)authentication.getPrincipal();
	        if (user == null) {
	            // TODO
	        	//throw new AuthTokenException("ChangePasswordAuthenticationProvider: User is NULL.");
	        }
	        if (!authentication.isAuthenticated()) {
	            try {
	                DateTime now = new DateTime();
	                now.minusHours(getChangePasswordTokenValidity());
	                if (now.isBefore(((User)authentication.getCredentials()).getChangePasswordRequestDate().getMillis())) {
	                	// TODO
	                	// throw new AuthTokenException("Registration token time stamp expired");
	                }
	            } catch (NullPointerException e) {
	            	// TODO
	                // throw new AuthTokenException("Registration token time stamp not found");
	            }
	            if (!((User)authentication.getCredentials()).getEnabled()) {
	                throw new DisabledException("User account is disabled");
	            }
	            authentication.setAuthenticated(true);
	        }
	        return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ChangePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	public void afterPropertiesSet() {
        Assert.notNull(this.changePasswordTokenValidity, "A changePasswordTokenValidity must be set");
    }

    public int getChangePasswordTokenValidity(){
        return changePasswordTokenValidity;
    }

    public void setChangePasswordTokenValidity(int changePasswordTokenValidity) {
        this.changePasswordTokenValidity = changePasswordTokenValidity;
    }

}
