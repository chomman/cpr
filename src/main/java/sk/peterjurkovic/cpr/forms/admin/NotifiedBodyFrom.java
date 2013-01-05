package sk.peterjurkovic.cpr.forms.admin;

import org.joda.time.DateTime;

import sk.peterjurkovic.cpr.entities.Address;
import sk.peterjurkovic.cpr.entities.User;

public class NotifiedBodyFrom {
	
	private Long id;
	
	private String name;
	
	private String notifiedBodyCode;
	
	private Address address;
	
	private String webpage;
	
	private String phone;
	
	private String fax;
	
	private String email;
	
	private Boolean etaCertificationAllowed; 
	
	private String description;
	
	private User createdBy;
	
	private User changedBy;
	
	private DateTime created;
	
	private DateTime changed;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotifiedBodyCode() {
		return notifiedBodyCode;
	}

	public void setNotifiedBodyCode(String notifiedBodyCode) {
		this.notifiedBodyCode = notifiedBodyCode;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEtaCertificationAllowed() {
		return etaCertificationAllowed;
	}

	public void setEtaCertificationAllowed(Boolean etaCertificationAllowed) {
		this.etaCertificationAllowed = etaCertificationAllowed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public DateTime getChanged() {
		return changed;
	}

	public void setChanged(DateTime changed) {
		this.changed = changed;
	}
	
	
}
