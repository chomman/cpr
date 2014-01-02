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
import javax.validation.constraints.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.NotEmpty;

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
	
	private Long id;
		
	private String csnName;

	private String csnOnlineId;
	
	private StandardStatus standardStatus;
	
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
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	@ManyToOne
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

	public StandardCsnChange getStandardCsnChangeById(Long id){
		if(CollectionUtils.isEmpty(standardCsnChanges)){
			return null;
		}
		for(final StandardCsnChange change : standardCsnChanges){
			if(change.getId() == id){
				return change;
			}
		}
		return null;
	}
}
