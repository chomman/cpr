package sk.peterjurkovic.cpr.service;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sk.peterjurkovic.cpr.test.AbstractTest;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserOnlinePublication;
import cz.nlfnorm.enums.OnlinePublication;
import cz.nlfnorm.enums.PortalProductType;
import cz.nlfnorm.services.PortalUserService;
import cz.nlfnorm.web.json.dto.SgpportalRequest;


public class PortalOrderTest extends AbstractTest {

	@Autowired
	private PortalUserService portalUserService;
	@Value("${mail.developer}")
	private String developerEmail;

	@Test
	public void testRequest(){
		 MultiValueMap<String, Object> args = new LinkedMultiValueMap<String, Object>();
		 args.add("user", getUser());
		 SgpportalRequest req = new SgpportalRequest();
		 User user =  getUser();
		 req.addOnlinePublication(getPublication(OnlinePublication.ANALYZA_REACH, user));
		 req.addOnlinePublication(getPublication(OnlinePublication.CHEMICKE_LATKY, user));
		 req.addOnlinePublication(getPublication(OnlinePublication.NORMY, user));
		 portalUserService.syncUser(user);
		 
		 req.addOnlinePublication(getPublication(OnlinePublication.NORMY, user));
		 req.addOnlinePublication(getPublication(OnlinePublication.STYK_S_POTRAV, user));
		 req.addOnlinePublication(getPublication(OnlinePublication.PRYZE, user));
		 req.addOnlinePublication(getPublication(OnlinePublication.ZIVOTNE_PRODTREDI, user));
		 
		 portalUserService.syncUserOnlinePublicaions(user);
	}
	
	
	private User getUser(){
		User user = new User();
		user.setId(5000l);
		user.setSgpPassword("1234456");
		user.setEmail(new LocalDateTime().toString("mm:ss") + developerEmail);
		user.setChanged(new LocalDateTime());
		return user;
	}
	
	private UserOnlinePublication getPublication(OnlinePublication pub, User user){
		UserOnlinePublication uop = new UserOnlinePublication();
		uop.setChanged(new LocalDateTime());
		uop.setPortalProduct(getPortalProduct(pub));
		uop.setValidity(new LocalDate());
		uop.setUser(user);
		user.getOnlinePublications().add(uop);
		return uop;
	}
	
	private PortalProduct getPortalProduct(OnlinePublication pub){
		PortalProduct p = new PortalProduct();
		p.setOnlinePublication(pub);
		p.setPortalProductType(PortalProductType.PUBLICATION);
		return p;
	}
}
