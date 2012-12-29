package sk.peterjurkovic.cpr.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.peterjurkovic.cpr.entities.User;


public class UserUtils {
	
	/**
	 * Vrati prihlaseneho uzivatela
	 * 
	 * @return User Prave prihlaseny uzivatel
	 */
	public static User getLoggedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            return (User)auth.getPrincipal();
        }
        return null;
    }
}
