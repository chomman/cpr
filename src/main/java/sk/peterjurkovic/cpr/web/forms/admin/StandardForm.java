package sk.peterjurkovic.cpr.web.forms.admin;

import com.drew.lang.annotations.NotNull;

import sk.peterjurkovic.cpr.entities.StandardGroup;

public class StandardForm {
	
	@NotNull
	private StandardGroup standardGroup;

	public StandardGroup getStandardGroup() {
		return standardGroup;
	}

	public void setStandardGroup(StandardGroup standardGroup) {
		this.standardGroup = standardGroup;
	}
	
	
	
}
