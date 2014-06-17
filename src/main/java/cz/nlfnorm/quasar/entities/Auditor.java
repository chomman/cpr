package cz.nlfnorm.quasar.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
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
public class Auditor extends User {

	public final static int TYPE_PRODUCT_ASSESSOR_A = 1;
	public final static int TYPE_PRODUCT_ASSESSOR_R = 2;
	public final static int TYPE_PRODUCT_SPECIALIST = 3;
	
	private static final long serialVersionUID = 895647134385058163L;
	
	private static final String EDUCATION_ACTIVE_MD = "1";
	private static final String EDUCATION_NON_ACTIVE_MD = "2";
	
	/* Personal data */
	private String degrees;
	private Integer itcId;
	private Country country;
	private Partner partner;
	private boolean inTraining;
	
	/* Formal and legal requirements: */
	private boolean ecrCardSigned;
	private boolean confidentialitySigned;
	private boolean cvDelivered;
	
	private LocalDateTime reassessmentDate;
	
	private AuditingTraining auditingTraining;
	
	private Map<String, AuditorEducation> education;
	private Map<Integer, AuditorSpecialist> specialist;

	private Set<AuditorExperience> auditorExperiences;
	private Set<AuditorEacCode> auditorsEacCodes;
	private Set<AuditorNandoCode> auditorsNandoCodes;
	
	
	public Auditor(){
		this.education = new HashMap<>();
		this.education.put(EDUCATION_ACTIVE_MD, new AuditorEducation());
		this.education.put(EDUCATION_NON_ACTIVE_MD, new AuditorEducation());
		this.specialist = new HashMap<>();
		this.specialist.put(TYPE_PRODUCT_ASSESSOR_A, new AuditorSpecialist());
		this.specialist.put(TYPE_PRODUCT_ASSESSOR_R, new AuditorSpecialist());
		this.specialist.put(TYPE_PRODUCT_SPECIALIST, new AuditorSpecialist());
		this.auditingTraining = new AuditingTraining(this);
		this.auditorsEacCodes = new HashSet<>();
		this.auditorsNandoCodes = new HashSet<>();
		this.auditorExperiences = new HashSet<>();
	}
	 
	@NotNull(message = "{error.auditor.itcId.notNull}")
	@Column(name = "itc_id", nullable = false, unique = true)
	public Integer getItcId() {
		return itcId;
	}
	public void setItcId(Integer itcId) {
		this.itcId = itcId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
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
	
	@ElementCollection
	@CollectionTable(name = "quasar_auditor_has_education")
	@MapKeyJoinColumn(name = "type")
	public Map<String, AuditorEducation> getEducation() {
		return education;
	}

	public void setEducation(Map<String, AuditorEducation> education) {
		this.education = education;
	}
	
	@ElementCollection
	@CollectionTable(name = "quasar_auditor_has_specialities")
	@MapKeyJoinColumn(name = "type")
	public Map<Integer, AuditorSpecialist> getSpecialist() {
		return specialist;
	}

	public void setSpecialist(Map<Integer, AuditorSpecialist> specialist) {
		this.specialist = specialist;
	}

	@PrimaryKeyJoinColumn
	@OneToOne(mappedBy = "auditor", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public AuditingTraining getAuditingTraining() {
		return auditingTraining;
	}

	public void setAuditingTraining(AuditingTraining auditingTraining) {
		this.auditingTraining = auditingTraining;
	}
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor", fetch = FetchType.LAZY)	
	public Set<AuditorEacCode> getAuditorsEacCodes() {
		return auditorsEacCodes;
	}

	public void setAuditorsEacCodes(Set<AuditorEacCode> auditorsEacCodes) {
		this.auditorsEacCodes = auditorsEacCodes;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor", fetch = FetchType.LAZY)
	public Set<AuditorNandoCode> getAuditorsNandoCodes() {
		return auditorsNandoCodes;
	}

	public void setAuditorsNandoCodes(Set<AuditorNandoCode> nandoCodes) {
		this.auditorsNandoCodes = nandoCodes;
	}

	@Column(name = "is_in_training")
	public boolean isInTraining() {
		return inTraining;
	}

	public void setInTraining(boolean inTraining) {
		this.inTraining = inTraining;
	}
		
	@Length(max = 20, message = "{error.auditor.degree.length}")
	@Column(name = "degrees", length = 25)
	public String getDegrees() {
		return degrees;
	}

	public void setDegrees(String degrees) {
		this.degrees = degrees;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor", fetch = FetchType.LAZY)	
	public Set<AuditorExperience> getAuditorExperiences() {
		return auditorExperiences;
	}

	public void setAuditorExperiences(Set<AuditorExperience> auditorExperiences) {
		this.auditorExperiences = auditorExperiences;
	}

	@Transient
	public boolean isIntenalAuditor(){
		return getId() < 1000;
	}
	
	/**
	 * Returns education of person for active Medical devices;
	 * @return education for active MD
	 */
	@Transient
	public AuditorEducation getForActiveMedicalDevice(){
		return education.get(EDUCATION_ACTIVE_MD);
	}
	
	
	/**
	 * Returns education of person for non-active Medical devices;
	 * @return education for non-active MD
	 */
	@Transient
	public AuditorEducation getForNonActiveMedicalDevice(){
		return education.get(EDUCATION_NON_ACTIVE_MD);
	}
	
	
	@Transient
	public boolean areFormalAndLegalReqiurementValid(){
		return isEcrCardSigned() && isConfidentialitySigned() && isCvDelivered();
	}

	
	@Transient
	public String getName(){
		return (getFirstName() != null ? getFirstName() : "") + " " +
			   (getLastName() != null ? getLastName() : "");
	}	
	
	
	
}
