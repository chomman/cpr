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
	private Country countery;
	private Partner partner;
	private boolean inTraining;
	
	/* Formal and legal requirements: */
	private boolean ecrCardSigned;
	private boolean confidentialitySigned;
	private boolean cvDelivered;
	
	private LocalDateTime reassessmentDate;
	
	private Map<String, AuditorEducation> education;
	
	/* Work experience (number of years):  */
	private int medicalDevicesIndustry;
	private int pharmeceuticalIndustry;
	private int relevantTestingLaboratory;
	private int notifiedBody;
	private int mscbForIso13485;
	private int medicalInstitution;
	private int hospital;
	private int electronicIt;
	private int scienceResearchAuthority;
	private int other;
	
	private AuditingTraining auditingTraining;
	
	private Set<AuditorEacCode> auditorsEacCodes;
	private Set<AuditorNandoCode> audotorsNandoCodes;
	
	/* Decision on the Specialist’s branch assignation  */
	private boolean activeMedicalDeviceSpecialist;
	private boolean nonActiveMedicalDeviceSpecialist;
	private boolean inVitroDiagnosticSpecialist;
	
	
	public Auditor(){
		this.education = new HashMap<>();
		this.education.put(EDUCATION_ACTIVE_MD, new AuditorEducation());
		this.education.put(EDUCATION_NON_ACTIVE_MD, new AuditorEducation());
		this.auditingTraining = new AuditingTraining(this);
		this.auditorsEacCodes = new HashSet<>();
		this.audotorsNandoCodes = new HashSet<>();
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
	
	@ElementCollection
	@CollectionTable(name = "quasar_auditor_has_education")
	@MapKeyJoinColumn(name = "type")
	public Map<String, AuditorEducation> getEducation() {
		return education;
	}

	public void setEducation(Map<String, AuditorEducation> education) {
		this.education = education;
	}
	
	@Column(name = "exp_medical_devices_inndustry")	
	public int getMedicalDevicesIndustry() {
		return medicalDevicesIndustry;
	}

	public void setMedicalDevicesIndustry(int medicalDevicesIndustry) {
		this.medicalDevicesIndustry = medicalDevicesIndustry;
	}

	@Column(name = "exp_pharmeceutical_industry")
	public int getPharmeceuticalIndustry() {
		return pharmeceuticalIndustry;
	}

	public void setPharmeceuticalIndustry(int pharmeceuticalIndustry) {
		this.pharmeceuticalIndustry = pharmeceuticalIndustry;
	}
	
	@Column(name = "exp_relevant_testing_laboratory")
	public int getRelevantTestingLaboratory() {
		return relevantTestingLaboratory;
	}

	public void setRelevantTestingLaboratory(int relevantTestingLaboratory) {
		this.relevantTestingLaboratory = relevantTestingLaboratory;
	}
	
	@Column(name = "exp_notified_body")
	public int getNotifiedBody() {
		return notifiedBody;
	}

	public void setNotifiedBody(int notifiedBody) {
		this.notifiedBody = notifiedBody;
	}
	
	@Column(name = "exp_mscb_for_iso13485")
	public int getMscbForIso13485() {
		return mscbForIso13485;
	}

	public void setMscbForIso13485(int mscbForIso13485) {
		this.mscbForIso13485 = mscbForIso13485;
	}
	
	@Column(name = "exp_medical_institution")
	public int getMedicalInstitution() {
		return medicalInstitution;
	}

	public void setMedicalInstitution(int medicalInstitution) {
		this.medicalInstitution = medicalInstitution;
	}

	@Column(name = "exp_hospital")
	public int getHospital() {
		return hospital;
	}

	public void setHospital(int hospital) {
		this.hospital = hospital;
	}

	@Column(name = "exp_electronic_it")
	public int getElectronicIt() {
		return electronicIt;
	}

	public void setElectronicIt(int electronicIt) {
		this.electronicIt = electronicIt;
	}
	
	@Column(name = "exp_science_research_authority")
	public int getScienceResearchAuthority() {
		return scienceResearchAuthority;
	}

	public void setScienceResearchAuthority(int scienceResearchAuthority) {
		this.scienceResearchAuthority = scienceResearchAuthority;
	}
	
	@Column(name = "exp_other")
	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}
	
		
	@PrimaryKeyJoinColumn
	@OneToOne(mappedBy = "auditor", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public AuditingTraining getAuditingTraining() {
		return auditingTraining;
	}

	public void setAuditingTraining(AuditingTraining auditingTraining) {
		this.auditingTraining = auditingTraining;
	}
	
	@Column(name = "is_specialist_for_active_mdd")
	public boolean isActiveMedicalDeviceSpecialist() {
		return activeMedicalDeviceSpecialist;
	}

	public void setActiveMedicalDeviceSpecialist(
			boolean activeMedicalDeviceSpecialist) {
		this.activeMedicalDeviceSpecialist = activeMedicalDeviceSpecialist;
	}
	
	@Column(name = "is_specialist_for_non_active_mdd")
	public boolean isNonActiveMedicalDeviceSpecialist() {
		return nonActiveMedicalDeviceSpecialist;
	}

	public void setNonActiveMedicalDeviceSpecialist(
			boolean nonActiveMedicalDeviceSpecialist) {
		this.nonActiveMedicalDeviceSpecialist = nonActiveMedicalDeviceSpecialist;
	}
	
	@Column(name = "is_specialist_for_invitro_diagnostic")
	public boolean isInVitroDiagnosticSpecialist() {
		return inVitroDiagnosticSpecialist;
	}

	public void setInVitroDiagnosticSpecialist(boolean inVitroDiagnosticSpecialist) {
		this.inVitroDiagnosticSpecialist = inVitroDiagnosticSpecialist;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor", fetch = FetchType.LAZY)	
	public Set<AuditorEacCode> getAuditorsEacCodes() {
		return auditorsEacCodes;
	}

	public void setAuditorsEacCodes(Set<AuditorEacCode> auditorsEacCodes) {
		this.auditorsEacCodes = auditorsEacCodes;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditor", fetch = FetchType.LAZY)
	public Set<AuditorNandoCode> getAudotorsNandoCodes() {
		return audotorsNandoCodes;
	}

	public void setAudotorsNandoCodes(Set<AuditorNandoCode> audotorsNandoCodes) {
		this.audotorsNandoCodes = audotorsNandoCodes;
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
	
	/**
	 * Returs total work experience in years 
	 * @return total work experience in years
	 */
	
	@Transient
	public int getTotalWorkExperience(){
		return getMedicalDevicesWorkExperience() + other;	
	}
	
	/**
	 * Returs relevant work experience in Medical Device sector 
	 * @return int MD - relevat experience in years
	 */
	@Transient
	public int getMedicalDevicesWorkExperience(){
		return medicalDevicesIndustry +
				pharmeceuticalIndustry +
				relevantTestingLaboratory +
				notifiedBody + 
				mscbForIso13485 +
				medicalInstitution + 
				hospital +
				electronicIt +
				scienceResearchAuthority;
	}
	
	
	@Transient
	public boolean areFormalAndLegalReqiurementValid(){
		return isEcrCardSigned() && isConfidentialitySigned() && isCvDelivered();
	}

	
	
	
	
}
