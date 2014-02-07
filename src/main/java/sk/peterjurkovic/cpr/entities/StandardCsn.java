package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;

import sk.peterjurkovic.cpr.enums.StandardStatus;

/**
 * Asociacna entita, ktora prepaja normu a CSN.
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */


@Entity
@SequenceGenerator(name = "standard_has_csn_id_seq", sequenceName = "standard_has_csn_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "standard_csn")
public class StandardCsn extends AbstractEntity {

	
	private static final long serialVersionUID = 7791L;
			
	private String csnName;

	private String csnOnlineId;
	
	private StandardStatus standardStatus;
	
	private LocalDate statusDate;
	
	private String classificationSymbol;
	
	private String note;
	
	private StandardCsn replaceStandardCsn;
	
	private Set<StandardCsnChange> standardCsnChanges;
	
	public StandardCsn(){
		this.standardStatus = StandardStatus.NORMAL;
		this.standardCsnChanges = new HashSet<StandardCsnChange>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_has_csn_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	
	@Column
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name ="csn_name", length = 45)
	@NotEmpty(message = "Název ČSN musí být vyplněn.")
	public String getCsnName() {
		return csnName;
	}
	
	public void setCsnName(String csnName) {
		this.csnName = csnName;
	}

	@Column(name = "csn_online_id", length = 10)
	@Pattern(regexp = "(^[0-9]{1,10}$|)*", message = "ČSN online ID obsahuje neplatnou hodnotu")
	public String getCsnOnlineId() {
		return csnOnlineId;
	}

	public void setCsnOnlineId(String csnOnlineId) {
		this.csnOnlineId = csnOnlineId;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "standard_csn_status", length = 15)
	public StandardStatus getStandardStatus() {
		return standardStatus;
	}

	public void setStandardStatus(StandardStatus standardStatus) {
		this.standardStatus = standardStatus;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "replaced_standard_csn_id")
	public StandardCsn getReplaceStandardCsn() {
		return replaceStandardCsn;
	}

	public void setReplaceStandardCsn(StandardCsn replaceStandardCsn) {
		this.replaceStandardCsn = replaceStandardCsn;
	}
	
	@Column(name = "classification_symbol", length = 10)
	public String getClassificationSymbol() {
		return classificationSymbol;
	}

	public void setClassificationSymbol(String classificationSymbol) {
		this.classificationSymbol = classificationSymbol;
	}
	
	@OneToMany(mappedBy = "standardCsn", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<StandardCsnChange> getStandardCsnChanges() {
		return standardCsnChanges;
	}

	public void setStandardCsnChanges(Set<StandardCsnChange> standardCsnChanges) {
		this.standardCsnChanges = standardCsnChanges;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "status_date")
	public LocalDate getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(LocalDate statusDate) {
		this.statusDate = statusDate;
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
		StandardCsn other = (StandardCsn) obj;
		if (csnOnlineId == null) {
			if (other.csnOnlineId != null)
				return false;
		} else if (!csnOnlineId.equals(other.csnOnlineId))
			return false;
		return true;
	}
	
	
	@Transient
	public void merge(StandardCsn csn){
		csnName = csn.getCsnName();
		note = csn.getNote();
		classificationSymbol = csn.getClassificationSymbol();
		csnOnlineId = csn.getCsnOnlineId();
		standardStatus = csn.getStandardStatus();
		replaceStandardCsn = csn.getReplaceStandardCsn();
		statusDate = csn.getStatusDate();
	}
	
	
	
}
