package cz.nlfnorm.quasar.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.entities.AbstractLog;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.services.PartnerService;
import cz.nlfnorm.utils.UserUtils;

@Component
public class AccessUtils {
	
	private static PartnerService partnerService;
	
	private AccessUtils(){}
	
	@Autowired
	private PartnerService partnerService2;
	
	@PostConstruct
	public void init(){
		AccessUtils.partnerService = partnerService2;
	}
	
	public static boolean  isLoggedUserAuthorizedTo(AbstractLog log){
		final User user = UserUtils.getLoggedUser();
		if(user.isQuasarAdmin() || user.getId().equals(log.getCreatedBy().getId())){
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
	
	
	/**
	 * Return TRUE, If logged user is Manager of some Partner. 
	 * 
	 * @see {@link Partner} 
	 * @return TURE, If is logged user assigned in some Partner as Manager.
	 */
	public static boolean isLoggedUserPartnerManager(){
		return isUserPartnerManager(UserUtils.getLoggedUser());
	}
	
}
