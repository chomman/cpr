package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.User;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */
@Entity
@Table(name = "quasar_auditor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Auditor extends User {

	private static final long serialVersionUID = 895647134385058163L;
	
	/* Personal data */
	private Integer itcId;
	private Country countery;
	private Partner partner;
	
	/* Formal and legal requirements: */
	private boolean ecrCardSigned;
	private boolean confidentialitySigned;
	private boolean cvDelivered;
	
	private LocalDateTime reassessmentDate;
	
	public Auditor(){
		ecrCardSigned = false;
		confidentialitySigned = false;
		cvDelivered = false;
	}
	 
	@Column(name = "personal_itc_id")
	public Integer getItcId() {
		return itcId;
	}
	public void setItcId(Integer itcId) {
		this.itcId = itcId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
	public Country getCountery() {
		return countery;
	}
	public void setCountery(Country countery) {
		this.countery = countery;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	@Column(name = "is_ecr_card_signed", nullable = false)
	public boolean isEcrCardSigned() {
		return ecrCardSigned;
	}
	public void setEcrCardSigned(boolean ecrCardSigned) {
		this.ecrCardSigned = ecrCardSigned;
	}
	
	@Column(name = "is_confidentiality_signed", nullable = false)
	public boolean isConfidentialitySigned() {
		return confidentialitySigned;
	}
	public void setConfidentialitySigned(boolean confidentialitySigned) {
		this.confidentialitySigned = confidentialitySigned;
	}
	
	@Column(name = "is_cv_delivered", nullable = false)
	public boolean isCvDelivered() {
		return cvDelivered;
	}
	public void setCvDelivered(boolean cvDelivered) {
		this.cvDelivered = cvDelivered;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "reassessment_date")
	public LocalDateTime getReassessmentDate() {
		return reassessmentDate;
	}

	public void setReassessmentDate(LocalDateTime reassessmentDate) {
		this.reassessmentDate = reassessmentDate;
	}
	
	
	
	
	
}
