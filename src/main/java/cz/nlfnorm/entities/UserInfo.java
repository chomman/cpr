package cz.nlfnorm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.enums.PortalCountry;

@Entity
@Table(name = "user_info", schema = "public")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 4163770436758655663L;
	private User user;
	private String phone;
	
	private String city;
	private String street;
	private String zip;
	
	private String companyName;
	private String ico;
	private String dic;
	
	private PortalCountry portalCountry = PortalCountry.CR;
	
	private Boolean synced = true;
	
	@Id
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Pattern(regexp = "(^[+]?[()/0-9. -]{9,}$|)*", message = "{error.phone}")
	@Column(name = "phone", length = 25)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(max = 50)
	@Column(name = "city", length = 50)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(max = 50)
	@Column(name = "street", length = 50)
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Pattern(regexp = "(^\\d{3}\\s?\\d{2}$|)*", message = "{error.zip}")
	@Column(name = "zip", length = 6)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(name = "company_name", length = 50)
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Pattern(regexp = "(^\\d{8}$|)*",message = "{error.ico}")
	@Column(name = "ico", length = 8)
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	
	
	@Column(name = "dic", length = 15)
	public String getDic() {
		return dic;
	}
	public void setDic(String dic) {
		this.dic = dic;
	}
	
	@Column(name = "sgp_sync_ok", length = 15)
	public Boolean getSynced() {
		return synced;
	}
	public void setSynced(Boolean synced) {
		this.synced = synced;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "portal_country", length = 2, nullable = false)		
	public PortalCountry getPortalCountry() {
		return portalCountry;
	}
	public void setPortalCountry(PortalCountry portalCountry) {
		this.portalCountry = portalCountry;
	}
	
	
	@Override
	public String toString() {
		return "UserInfo [user=" + user + ", phone=" + phone + ", city=" + city
				+ ", street=" + street + ", zip=" + zip + ", companyName="
				+ companyName + ", ico=" + ico + ", dic=" + dic + "]";
	}
	
	@Transient
	public void merge(UserInfo userInfo){
		phone = userInfo.getPhone();
		city = userInfo.getCity();
		street = userInfo.getStreet();
		zip = userInfo.getZip();
		companyName = userInfo.getCompanyName();
		ico = userInfo.getIco();
		dic = userInfo.getDic();
		portalCountry = userInfo.getPortalCountry();
	}
	
	
}
