package cz.nlfnorm.web.forms.admin;

import org.apache.commons.lang.Validate;

import cz.nlfnorm.entities.Regulation;

public class RegulationForm {
	
	private Regulation regulation;
	private boolean eu;
	private boolean cs;
	private boolean sk;
	
	public RegulationForm(){
		regulation = new Regulation();
	}
	
	public RegulationForm(Regulation regulation){
		Validate.notNull(regulation);
		
	}
	
	public Regulation getRegulation() {
		return regulation;
	}
	public void setRegulation(Regulation regulation) {
		this.regulation = regulation;
	}
	public boolean isEu() {
		return eu;
	}
	public void setEu(boolean eu) {
		this.eu = eu;
	}
	public boolean isCs() {
		return cs;
	}
	public void setCs(boolean cs) {
		this.cs = cs;
	}
	public boolean isSk() {
		return sk;
	}
	public void setSk(boolean sk) {
		this.sk = sk;
	}
	
	
}
