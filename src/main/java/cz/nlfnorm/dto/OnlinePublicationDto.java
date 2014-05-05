package cz.nlfnorm.dto;

import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.entities.UserOnlinePublication;

public class OnlinePublicationDto {
	
	private UserOnlinePublication userOnlinePublication;
	private PortalProduct product;
	
	
	public UserOnlinePublication getUserOnlinePublication() {
		return userOnlinePublication;
	}
	public void setUserOnlinePublication(UserOnlinePublication userOnlinePublication) {
		this.userOnlinePublication = userOnlinePublication;
	}
	public PortalProduct getProduct() {
		return product;
	}
	public void setProduct(PortalProduct product) {
		this.product = product;
	}
	
	
	
}
