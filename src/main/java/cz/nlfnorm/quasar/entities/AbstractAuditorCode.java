package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.dao.impl.IdentifiableByLong;
import cz.nlfnorm.entities.NotifiedBody;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAuditorCode implements Serializable, IdentifiableByLong{
	
	private Long id;

	private Auditor auditor;
	
	/**
	 * Specific reason for the approval (in case of missing review)
	 */
	private boolean approved;
	
	/**
	 * Reason details description for the approval
	 */
	private String reasonForApproval;

	/**
	 * If is NOT NULL, given NB approved for
	 */
	private NotifiedBody notifiedBody;
		

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	@Override
	public Long getId() {
		return id;
	}
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "auditor_id")
	public Auditor getAuditor(){
		return auditor;
	}
		

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	@Column(name = "is_approved")
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Length(max = 255, message = "{error.AbstractAuditorFunction.reasonForApproval}")
	@Column(name = "reason_for_approval")
	public String getReasonForApproval() {
		return reasonForApproval;
	}

	public void setReasonForApproval(String reasonForApproval) {
		this.reasonForApproval = reasonForApproval;
	}

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "notified_body_id")
	public NotifiedBody getNotifiedBody() {
		return notifiedBody;
	}

	public void setNotifiedBody(NotifiedBody notifiedBody) {
		this.notifiedBody = notifiedBody;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAuditorCode other = (AbstractAuditorCode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
