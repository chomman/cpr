package sk.peterjurkovic.cpr.entities;

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
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;


@Entity
@Table(name = "standard_csn_has_change")
@SequenceGenerator(name = "standard_csn_change_id_seq", sequenceName = "standard_csn_change_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class StandardCsnChange extends AbstractEntity {

	
	private static final long serialVersionUID = 5153211394849046656L;

	private String changeCode;

	private String csnOnlineId;
	
	private StandardCsn standardCsn;
	
	private LocalDate date;
	
	private String note;
	
	public StandardCsnChange(){
		setCreated(new LocalDateTime());
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_csn_change_id_seq")
	public Long getId() {
		return super.getId();
	}

	
	@NotBlank
	@Column(name = "change_code")
	public String getChangeCode() {
		return changeCode;
	}


	public void setChangeCode(String changeCode) {
		this.changeCode = changeCode;
	}

	
	@Column(name = "csnonline_id", length = 10)
	public String getCsnOnlineId() {
		return csnOnlineId;
	}


	public void setCsnOnlineId(String csnOnlineId) {
		this.csnOnlineId = csnOnlineId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public StandardCsn getStandardCsn() {
		return standardCsn;
	}


	public void setStandardCsn(StandardCsn standardCsn) {
		this.standardCsn = standardCsn;
	}

	@Column(name = "change_note")
	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "change_date")
	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((csnOnlineId == null) ? 0 : csnOnlineId.hashCode());
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
		StandardCsnChange other = (StandardCsnChange) obj;
		if (csnOnlineId == null) {
			if (other.csnOnlineId != null)
				return false;
		} else if (!csnOnlineId.equals(other.csnOnlineId))
			return false;
		return true;
	}

	
	
}
