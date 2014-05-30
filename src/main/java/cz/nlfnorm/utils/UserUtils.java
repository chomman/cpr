package cz.nlfnorm.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserOnlinePublication;


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
		final User user = UserUtils.getLoggedUser();
		if(user == null || !user.isPortalUser()){
			return false;
		}
		return true;
	}
	
	
	public static boolean isPortalAuthorized(){
		final User user = getLoggedUser();
		if(user != null){
			return user.isPortalAuthorized();
		}
		return false;
	}
	
	
	
	public static Integer getPortalProductValidityInDays(final Long productId){
		final User user = UserUtils.getLoggedUser();
		if(user == null){
			return null;
		}
		final UserOnlinePublication uop = user.getUserOnlinePublication(productId);
		if(uop == null){
			return null;
		}
		return DateTimeUtils.daysLeft(uop.getValidity());
	}
}
