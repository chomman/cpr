package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import cz.nlfnorm.enums.StandardStatus;
import cz.nlfnorm.utils.CommonUtils;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractStandard extends AbstractEntity{
	
	private static final long serialVersionUID = 77324658435465431L;

	private StandardStatus standardStatus;
	
	private LocalDate statusDate;
	
	private LocalDate released;
		
	public AbstractStandard(){
		this.standardStatus = StandardStatus.HARMONIZED;
	}
	
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "status_date")
	public LocalDate getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(LocalDate statusDate) {
		this.statusDate = statusDate;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "standard_status", length = 25)
	public StandardStatus getStandardStatus() {
		return standardStatus;
	}

	public void setStandardStatus(StandardStatus standardStatus) {
		this.standardStatus = standardStatus;
	}
	
	
	@Transient
	public String getStatusClass(){
		if(standardStatus != null){
			return standardStatus.getCssClass();
		}
		return "";
	}
	
	@Transient
	public boolean getIsCanceled(){
		if(standardStatus == null){
			return false;
		}
		if(standardStatus.equals(StandardStatus.CANCELED_HARMONIZED)){
			return true;
		}
		return false;
	}
	
	@Transient
	public boolean getIsConcurrentValid(){
		if(standardStatus == null){
			return false;
		}
		if(standardStatus.equals(StandardStatus.CONCURRENT)){
			return true;
		}
		return false;
	}
	
	
	@Transient
	public boolean isNew(){
		return CommonUtils.isObjectNew(getReleased());
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "released_date")
	public LocalDate getReleased() {
		return released;
	}


	public void setReleased(LocalDate released) {
		this.released = released;
	}
	
	
	
	
}
