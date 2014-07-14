package cz.nlfnorm.quasar.security;

import org.springframework.security.access.AccessDeniedException;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.utils.UserUtils;

public class AccessUtils {
	
	public static boolean  isLoggedUserAuthorizedTo(AbstractLog log){
		final User user = UserUtils.getLoggedUser();
		if(user.isQuasarAdmin() || user.getId().equals(log.getAuditor().getId())){
			return true;
		}
		return false;
	}
	
	public static void validateAuthorizationFor(final AbstractLog log){
		if(!isLoggedUserAuthorizedTo(log)){
			throw new AccessDeniedException(UserUtils.getLoggedUser() +  " tried to access " + log);
		}
	}
}
