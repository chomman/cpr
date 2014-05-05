package cz.nlfnorm.web.forms.admin;

import org.joda.time.LocalDate;

public class ChangeValidityForm {
	
	private Long id;
	private LocalDate date;
	private boolean isRegistration;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public boolean isRegistration() {
		return isRegistration;
	}
	public void setRegistration(boolean isRegistration) {
		this.isRegistration = isRegistration;
	}
	
	
}
