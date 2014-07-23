package cz.nlfnorm.quasar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.utils.UserUtils;

public class AccessUtils {
	
	private static PartnerService partnerService;
	
	@Autowired
	private PartnerService partnerService2;
	
	public void init(){
		AccessUtils.partnerService = partnerService2;
	}
	
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
	
	public static boolean isUserPartnerManager(User user){
		if(user.isQuasarAdmin()){
			return true;
		}
		return partnerService.isUserPartnerManager(user);
	}
	
	
	public static boolean isLoggedUserPartnerManager(){
		return isUserPartnerManager(UserUtils.getLoggedUser());
	}
}
