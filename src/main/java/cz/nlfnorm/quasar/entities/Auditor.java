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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

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
	
	private LocalDate reassessmentDate;
	
	/* Training including Auditing experience */
	/**
	 * Aproved auditor for ISO 9001?
	 */
	private boolean aprovedForIso9001;
	/**
	 * Aproved auditor for ISO 13485?
	 */
	private boolean aprovedForIso13485;	
	/**
	 * Training ISO 9001 (hours)
	 */
	private int iso9001;
	/**
	 * Training ISO 13485 (hours)
	 */
	private int iso13485;
	/**
	 * Training MDD (hours)
	 */
	private int mdd;
	/**
	 * Training IVD (hours)
	 */
	private int ivd;
	/**
	 * Training NB 1023 procedures (hours)
	 */
	private int nb1023Procedures;
	/**
	 * Total count of user audits
	 */
	private int totalAudits;
	/**
	 * Sum of audits in days
	 */
	private int totalAuditdays;
			
	private Map<String, AuditorEducation> education;
	private Map<Integer, AuditorSpecialist> specialist;

	private Set<AuditorExperience> auditorExperiences;
	private Set<AuditorEacCode> auditorsEacCodes;
	private Set<AuditorNandoCode> auditorsNandoCodes;
	private Set<SpecialTraining> specialTrainings;
	
	public Auditor(){
		this.education = new HashMap<>();
		this.education.put(EDUCATION_ACTIVE_MD, new AuditorEducation());
		this.education.put(EDUCATION_NON_ACTIVE_MD, new AuditorEducation());
		this.specialist = new HashMap<>();
		this.specialist.put(TYPE_PRODUCT_ASSESSOR_A, new AuditorSpecialist());
		this.specialist.put(TYPE_PRODUCT_ASSESSOR_R, new AuditorSpecialist());
		this.specialist.put(TYPE_PRODUCT_SPECIALIST, new AuditorSpecialist());
		this.auditorsEacCodes = new HashSet<>();
		this.auditorsNandoCodes = new HashSet<>();
		this.auditorExperiences = new HashSet<>();
		this.specialTrainings = new HashSet<>();
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
	public LocalDate getReassessmentDate() {
		return reassessmentDate;
	}

	public void setReassessmentDate(LocalDate reassessmentDate) {
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
	
	@Column(name = "is_aproved_for_iso9001")
	public boolean isAprovedForIso9001() {
		return aprovedForIso9001;
	}

	public void setAprovedForIso9001(boolean aprovedForIso9001) {
		this.aprovedForIso9001 = aprovedForIso9001;
	}
	
	@Column(name = "is_aproved_for_iso13485")
	public boolean isAprovedForIso13485() {
		return aprovedForIso13485;
	}

	public void setAprovedForIso13485(boolean aprovedForIso13485) {
		this.aprovedForIso13485 = aprovedForIso13485;
	}
	
	@Min( value = 0 )
	@Column(name = "iso9001_hours")
	public int getIso9001() {
		return iso9001;
	}
	
	public void setIso9001(int iso9001) {
		this.iso9001 = iso9001;
	}
	
	@Min( value = 0 )
	@Column(name = "iso13485_hours")
	public int getIso13485() {
		return iso13485;
	}

	public void setIso13485(int iso13485) {
		this.iso13485 = iso13485;
	}
	
	@Min( value = 0 )
	@Column(name = "mdd_hours")
	public int getMdd() {
		return mdd;
	}

	public void setMdd(int mdd) {
		this.mdd = mdd;
	}
	
	@Min( value = 0 )
	@Column(name = "ivd_hours")
	public int getIvd() {
		return ivd;
	}

	public void setIvd(int ivd) {
		this.ivd = ivd;
	}
	
	@Min( value = 0 )
	@Column(name = "nb1023_procedures_hours")
	public int getNb1023Procedures() {
		return nb1023Procedures;
	}

	public void setNb1023Procedures(int nb1023Procedures) {
		this.nb1023Procedures = nb1023Procedures;
	}
	
	@Min( value = 0 )
	@Column(name = "totoal_of_audits")
	public int getTotalAudits() {
		return totalAudits;
	}

	public void setTotalAudits(int totalAudits) {
		this.totalAudits = totalAudits;
	}
	
	@Min( value = 0 )
	@Column(name = "totoal_of_auditdayes")
	public int getTotalAuditdays() {
		return totalAuditdays;
	}

	public void setTotalAuditdays(int totalAuditdays) {
		this.totalAuditdays = totalAuditdays;
	}

	
	@Transient
	public void incrementAuditDays(int days){
		this.totalAuditdays += days;
	}
	
	@Transient
	public void incrementTotalAudits(int count){
		this.totalAudits += count;
	}

	@OneToMany(mappedBy = "auditor", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<SpecialTraining> getSpecialTrainings() {
		return specialTrainings;
	}

	public void setSpecialTrainings(Set<SpecialTraining> specialTrainings) {
		this.specialTrainings = specialTrainings;
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
	public boolean getAreFormalAndLegalReqiurementValid(){
		return isEcrCardSigned() && isConfidentialitySigned() && isCvDelivered();
	}

	@Transient
	public int getTotalWorkExperience(){
		int totalExperience = 0;
		for(AuditorExperience exp : auditorExperiences){
			totalExperience += exp.getYears();
		}
		return totalExperience;
	}
	
	@Transient
	public int getTotalWorkExperienceInMedicalDevices(){
		int mdExperience = 0;
		for(AuditorExperience exp : auditorExperiences){
			if(exp.getExperience().isMedicalDeviceExperience()){
				mdExperience += exp.getYears();
			}
		}
		return mdExperience;
	}
		
	public void mergePersonalData(final Auditor form){
		setEmail(form.getEmail());
		setEnabled(form.getEnabled());
		setDegrees(form.getDegrees());
		setFirstName(form.getFirstName());
		setLastName(form.getLastName());
		setId(form.getId());
		setReassessmentDate(form.getReassessmentDate());
		setCountry(form.getCountry());
		setPartner(form.getPartner());
		setEcrCardSigned(form.isEcrCardSigned());
		setConfidentialitySigned(form.isConfidentialitySigned());
		setCvDelivered(form.isCvDelivered());
		setEducation(form.getEducation());
		setInTraining(form.isInTraining());
		this.aprovedForIso9001 = form.isAprovedForIso9001();
		this.aprovedForIso13485 = form.isAprovedForIso13485();
		this.iso9001 = form.getIso9001();
		this.iso13485 = form.getIso13485();
		this.mdd = form.getMdd();
		this.ivd = form.getIvd();
		this.nb1023Procedures = form.getNb1023Procedures();
		this.totalAudits = form.getTotalAudits();
		this.totalAuditdays = form.getTotalAuditdays();
	}
}
