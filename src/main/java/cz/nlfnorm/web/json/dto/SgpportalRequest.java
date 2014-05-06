package cz.nlfnorm.web.json.dto;

import java.util.ArrayList;
import java.util.List;

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
}
