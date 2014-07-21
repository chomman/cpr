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
	 * Specific reason for the approval (in case of missing review)
	 */
	private boolean itcApproved;
	
	private boolean refused;
	
	private String reasonOfRefusal;
	
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
	
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "eac_code_id", nullable = false)
	public EacCode getEacCode() {
		return eacCode;
	}

	public void setEacCode(EacCode eacCode) {
		this.eacCode = eacCode;
	}
	
	
	@Column(name = "is_itc_approved")
	public boolean isItcApproved() {
		return itcApproved;
	}

	public void setItcApproved(boolean itcApproved) {
		this.itcApproved = itcApproved;
	}
	
	@Column(name = "refused")
	public boolean isRefused() {
		return refused;
	}

	public void setRefused(boolean refused) {
		this.refused = refused;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "notified_body_id")
	public NotifiedBody getNotifiedBody() {
		return notifiedBody;
	}

	public void setNotifiedBody(NotifiedBody notifiedBody) {
		this.notifiedBody = notifiedBody;
	}

	@Column(name = "reason_of_refusal")
	public String getReasonOfRefusal() {
		return reasonOfRefusal;
	}

	public void setReasonOfRefusal(String reasonOfRefusal) {
		this.reasonOfRefusal = reasonOfRefusal;
	}
	
	@Transient 
	public boolean isGranted(){
		return !isRefused() &&
			   getNotifiedBody() != null ||
			   isItcApproved() ||
			   getTotalNumberOfAudits() >= getEacCode().getThreshold();
	}
	
	@Transient
	public int getNoOfAuditsInTraining(){
		return getNoOfAuditsInTraining(eacCode.getThreshold());
	}
}
