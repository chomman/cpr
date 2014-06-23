package cz.nlfnorm.quasar.entities;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cz.nlfnorm.entities.NotifiedBody;

/**
 * QUASAR entity
 * 
 * Represents QS Auditor EAC code
 * 
 * @author Peter Jurkovic
 * @date Jun 13, 2014
 */
@Entity
@Table(name = "quasar_auditor_has_eac_code", uniqueConstraints = @UniqueConstraint(columnNames = {"eac_code_id", "auditor_id"}) )
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "quasar_auditor_has_eac_code_id_seq", sequenceName = "quasar_auditor_has_eac_code_id_seq", initialValue = 1, allocationSize =1)
public class AuditorEacCode extends AbstractAuditorCode {

	private static final long serialVersionUID = 6370251380332649136L;
	
	private EacCode eacCode;
	
	/**
	 * Number of NB 1023 audits in TA
	 */
	private int numberOfNbAudits;
	/**
	 * Number of ISO 13485 audits in TA
	 */
	private int numberOfIso13485Audits;
	
	/**
	 * Specific reason for the approval (in case of missing review)
	 */
	private boolean itcApproval;
	
	/**
	 * If is NOT NULL, given NB approved for
	 */
	private NotifiedBody notifiedBody;

	public AuditorEacCode(){}
	
	public AuditorEacCode(Auditor auditor, EacCode eacCode){
		setAuditor(auditor);
		this.eacCode = eacCode;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_auditor_has_eac_code_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "eac_code_id")
	public EacCode getEacCode() {
		return eacCode;
	}

	public void setEacCode(EacCode eacCode) {
		this.eacCode = eacCode;
	}
	
	@Min(value = 0)
	@Column(name = "number_of_nb_audits")
	public int getNumberOfNbAudits() {
		return numberOfNbAudits;
	}

	public void setNumberOfNbAudits(int numberOfNBAudits) {
		this.numberOfNbAudits = numberOfNBAudits;
	}

	@Min(value = 0)
	@Column(name = "number_of_iso13485_audits")
	public int getNumberOfIso13485Audits() {
		return numberOfIso13485Audits;
	}

	public void setNumberOfIso13485Audits(int numberOfIso13485Audits) {
		this.numberOfIso13485Audits = numberOfIso13485Audits;
	}
	
	
	@Column(name = "is_itc_approved")
	public boolean isItcApproved() {
		return itcApproval;
	}

	public void setItcApproved(boolean itcApproved) {
		this.itcApproval = itcApproved;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "notified_body_id")
	public NotifiedBody getNotifiedBody() {
		return notifiedBody;
	}

	public void setNotifiedBody(NotifiedBody notifiedBody) {
		this.notifiedBody = notifiedBody;
	}

	@Transient
	public int getTotalNumberOfAudits(){
		return getNumberOfIso13485Audits() + getNumberOfNbAudits();
	}
	
	@Transient 
	public boolean isGranted(){
		return getNotifiedBody() != null ||
			   isItcApproved() ||
			   getTotalNumberOfAudits() >= getEacCode().getThreshold();
	}
}
