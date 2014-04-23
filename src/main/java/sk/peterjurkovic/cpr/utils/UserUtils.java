package sk.peterjurkovic.cpr.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.peterjurkovic.cpr.entities.User;


public class UserUtils {
	
	
	public static User getLoggedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            return (User)auth.getPrincipal();
        }
        return null;
    }
	
	
	public static boolean hasLoggedUserRightToEdit(){
		User user = UserUtils.getLoggedUser();
		if(user == null || !user.isPortalUser()){
			return false;
		}
		return true;
	}
}
