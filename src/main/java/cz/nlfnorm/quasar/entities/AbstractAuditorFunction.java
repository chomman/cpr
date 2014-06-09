package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.dao.impl.IdentifiableByLong;

@MappedSuperclass
public abstract class AbstractAuditorFunction implements Serializable, IdentifiableByLong{
	
	private static final long serialVersionUID = 628057871816576949L;

	private Long id;
	
	@SuppressWarnings("unused")
	private Auditor auditor;
	
	/**
	 * Specific reason for the approval (in case of missing review)
	 */
	private boolean approved;
	
	/**
	 * Reason details description for the approval
	 */
	private String reasonForApproval;

	
		

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	
	public abstract Auditor getAuditor();
		

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
		AbstractAuditorFunction other = (AbstractAuditorFunction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
