package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * Entita reprezentuje zmenu eHN
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Dec 4, 2013
 *
 */

@Entity
@Table(name = "standard_change")
@SequenceGenerator(name = "standard_change_id_seq", sequenceName = "standard_change_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class StandardChange extends AbstractEntity{
	

	private static final long serialVersionUID = 3952649472754698284L;
		
	private String changeCode;
	
	private LocalDate startValidity;
	
	private LocalDate stopValidity;
	
	private String note;
	
	private Standard standard;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_change_id_seq")
	public Long getId() {
		return super.getId();
	}

	
	@Column(name = "change_code")
	public String getChangeCode() {
		return changeCode;
	}

	public void setChangeCode(String changeCode) {
		this.changeCode = changeCode;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "start_validity")
	public LocalDate getStartValidity() {
		return startValidity;
	}

	public void setStartValidity(LocalDate startValidity) {
		this.startValidity = startValidity;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "stop_validity")
	public LocalDate getStopValidity() {
		return stopValidity;
	}

	public void setStopValidity(LocalDate stopValidity) {
		this.stopValidity = stopValidity;
	}
	
	@Column(name = "change_note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return standard;
	}


	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	
	public void merge(StandardChange standardChange){
		setChangeCode(standardChange.getChangeCode());
		setNote(standardChange.getNote());
		setStartValidity(standardChange.getStartValidity());
		setStopValidity(standardChange.getStopValidity());
		setEnabled(standardChange.getEnabled());
		setChanged(new LocalDateTime());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((changeCode == null) ? 0 : changeCode.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardChange other = (StandardChange) obj;
		if (changeCode == null) {
			if (other.changeCode != null)
				return false;
		} else if (!changeCode.equals(other.changeCode))
			return false;
		return true;
	}
	
	
}
