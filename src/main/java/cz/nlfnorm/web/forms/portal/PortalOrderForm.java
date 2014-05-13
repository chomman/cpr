package cz.nlfnorm.web.forms.portal;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.entities.PortalCurrency;
import cz.nlfnorm.entities.PortalOrder;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.enums.PortalOrderSource;

public class PortalOrderForm extends BaseUserForm{
	
	@NotEmpty(message = "{error.email.empty}")
	@Email(message = "{error.email}")
	private String email;
	
	@NotNull
	private PortalCurrency portalCurrency = PortalCurrency.CZK;
	
	@NotNull
	private PortalOrderSource portalOrderSource = PortalOrderSource.NLFNORM;
		
	@NotEmpty(message = "{portalOrderForm.portalProductItems.empty}")
	private List<Long> portalProductItems = new ArrayList<Long>();
	
	public PortalOrderForm(){}
	
	public PortalOrderForm(PortalCurrency currency){
		this.portalCurrency = currency;
	}
	
	public PortalCurrency getPortalCurrency() {
		return portalCurrency;
	}
	public void setPortalCurrency(PortalCurrency portalCurrency) {
		this.portalCurrency = portalCurrency;
	}
		
	public List<Long> getPortalProductItems() {
		return portalProductItems;
	}

	public void setPortalProductItems(List<Long> portalProductItems) {
		this.portalProductItems = portalProductItems;
	}
	
	public PortalOrderSource getPortalOrderSource() {
		return portalOrderSource;
	}

	public void setPortalOrderSource(PortalOrderSource portalOrderSource) {
		this.portalOrderSource = portalOrderSource;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public void setUser(final User user){
		setId(user.getId());
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setEmail(user.getEmail());
		if(user.getUserInfo() != null){
			getUserInfo().merge(user.getUserInfo());
		}
	}
	
	public PortalOrder toPortalOrder(){
		PortalOrder order = new PortalOrder();
		order.setVat(Constants.VAT);
		order.setCurrency(getPortalCurrency());
		order.setPortalOrderSource(getPortalOrderSource());
		order.setPhone(getUserInfo().getPhone());
		order.setFirstName(getFirstName());
		order.setLastName(getLastName());
		order.setEmail(getEmail());
		order.setCity(getUserInfo().getCity());
		order.setZip(getUserInfo().getZip());
		order.setStreet(getUserInfo().getStreet());
		
		order.setCompanyName(getUserInfo().getCompanyName());
		order.setDic(getUserInfo().getDic());
		order.setIco(getUserInfo().getIco());
		return order;
	}

	@Override
	public String toString() {
		return "PortalOrderForm [email=" + email + ", portalCurrency="
				+ portalCurrency + ", portalOrderSource=" + portalOrderSource
				+ ", portalProductItems=" + portalProductItems + ", getId()="
				+ getId() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getUserInfo()="
				+ getUserInfo() + "]";
	}
	
	
	
}
