package sk.peterjurkovic.cpr.spring.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import sk.peterjurkovic.cpr.entities.User;



@SuppressWarnings("serial")
public class ChangePasswordAuthenticationToken extends AbstractAuthenticationToken{
	 
	private User principal;
    private Object token;


    public ChangePasswordAuthenticationToken(User principal, Object registrationToken) {
        super(principal != null ? principal.getAuthorities() : null);
        this.principal = principal;
        this.token = principal;
        setAuthenticated(principal != null && principal.getChangePasswordRequestToken() != null
                && principal.getChangePasswordRequestToken().equals(registrationToken));
    }

    public Object getCredentials() {
        return token;
    }

    public Object getPrincipal() {
        return principal;
    }
    
}
