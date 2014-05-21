package cz.nlfnorm.web.json.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.entities.UserOnlinePublication;

public class SgpportalRequest {
			
	private SgpportalUser user;
	
	private List<SgpportalOnlinePublication> publications = new ArrayList<SgpportalOnlinePublication>();
	
	public SgpportalUser getUser() {
		return user;
	}
	public void setUser(SgpportalUser user) {
		this.user = user;
	}
	
	public List<SgpportalOnlinePublication> getPublications() {
		return publications;
	}
	public void setPublications(List<SgpportalOnlinePublication> publications) {
		this.publications = publications;
	}
	
	public void addItem(SgpportalOnlinePublication uop){
		if(uop != null){
			publications.add(uop);
		}
	}
	
	public void addOnlinePublication(UserOnlinePublication uop){
		SgpportalOnlinePublication sgpPublication = new SgpportalOnlinePublication();
		sgpPublication.setChanged(uop.getChanged());
		sgpPublication.setCode(uop.getPortalProduct().getOnlinePublication().getCode());
		sgpPublication.setValidity(uop.getValidity());
		sgpPublication.setUserId(uop.getUser().getId());
		publications.add(sgpPublication);
	}
	
	public void addAllUserOnlinePublications(List<UserOnlinePublication> list){
		for(UserOnlinePublication uop : list){
			addOnlinePublication(uop);
		}
	}
	
	public void createSqpAccessFor(final User user){
		Validate.notNull(user);
		if(user.getHasActiveRegistration()){
			SgpportalOnlinePublication sop = new SgpportalOnlinePublication();
			sop.setChanged(new LocalDateTime());
			sop.setCode(SgpportalOnlinePublication.SGP_PORTAL_ACCESS);
			sop.setUserId(user.getId());
			if(user.isAdministrator()){
				// Admin has unlimited access years access (5 years)
				sop.setValidity(new LocalDate().plusYears(5));
			}else{
				sop.setValidity(user.getRegistrationValidity());
			}
			addItem(sop);
		}
	}
		
}
