package sk.peterjurkovic.cpr.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;

/**
 * Asociacna entita, ktora prepaja normu a CSN.
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */


@Entity
@SequenceGenerator(name = "standard_has_csn_id_seq", sequenceName = "standard_has_csn_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "standard_csn")
public class StandardCsn extends AbstractStandardCsn {

	private static final long serialVersionUID = 7791L;
				
	private String classificationSymbol;
		
	private StandardCsn replaceStandardCsn;
	
	private Set<StandardCsnChange> standardCsnChanges;
	
	public StandardCsn(){
		this.standardCsnChanges = new HashSet<StandardCsnChange>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_has_csn_id_seq")
	public Long getId() {
		return super.getId();
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
	
	
	@Transient
	public void merge(StandardCsn csn){
		setCsnName(csn.getCsnName());;
		setNote(csn.getNote());
		setCsnOnlineId( csn.getCsnOnlineId());
		setStandardStatus(csn.getStandardStatus());
		setStatusDate( csn.getStatusDate());
		classificationSymbol = csn.getClassificationSymbol();
		replaceStandardCsn = csn.getReplaceStandardCsn();
	}
	
		
	@Transient
	public boolean getHasChanges(){
		return CollectionUtils.isNotEmpty(standardCsnChanges);
	}
	
	
}
