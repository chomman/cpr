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

import org.apache.commons.lang.StringUtils;
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
	private double rating;
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
	private int trainingIso9001InHours;
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
	 * Training AIMD (hours)
	 */
	private int aimd;
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
	

	/* Product Assessor-R */
	private int tfTrainingInHours;
	private int totalTfReviews;
	
	/* Product Specialist */
	private int ddTrainingInHours;
	private int totalDdReviews;

	private String otherEmails;
	private boolean recentActivitiesApprovedForQsAuditor;
	private boolean recentActivitiesApprovedForProductAssessorA;
	private boolean recentActivitiesApprovedForProductAssessorR;
	private boolean recentActivitiesApprovedForProductSpecialist;
	
	/**
	 * Working experience in research and development, 
	 * production or quality control with medical devices or drugs
	 */
	private double researchDevelopmentExperienceInYears = 0;
			
	private Map<String, AuditorEducation> education;
	private Map<Integer, AuditorSpecialist> specialist;

	private Set<AuditorExperience> auditorExperiences;
	private Set<AuditorEacCode> auditorsEacCodes;
	private Set<AuditorNandoCode> auditorsNandoCodes;
	private Set<SpecialTraining> specialTrainings;
	
	private int auditDaysInRecentYear;
	private int trainingHoursInRecentYear;
	
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
	public int getTrainingIso9001InHours() {
		return trainingIso9001InHours;
	}
	
	public void setTrainingIso9001InHours(int iso9001) {
		this.trainingIso9001InHours = iso9001;
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
	@Column(name = "aimd_hours")	
	public int getAimd() {
		return aimd;
	}

	public void setAimd(int aimd) {
		this.aimd = aimd;
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
	@Column(name = "total_of_audits")
	public int getTotalAudits() {
		return totalAudits;
	}

	public void setTotalAudits(int totalAudits) {
		this.totalAudits = totalAudits;
	}
	
	@Min( value = 0 )
	@Column(name = "total_of_auditdayes")
	public int getTotalAuditdays() {
		return totalAuditdays;
	}

	public void setTotalAuditdays(int totalAuditdays) {
		this.totalAuditdays = totalAuditdays;
	}
	
	@Min( value = 0 )
	@Column(name = "tf_training_in_hours")	
	public int getTfTrainingInHours() {
		return tfTrainingInHours;
	}

	public void setTfTrainingInHours(int tfTrainingInHours) {
		this.tfTrainingInHours = tfTrainingInHours;
	}
	@Min( value = 0 )
	@Column(name = "total_tf_reviews")	
	public int getTotalTfReviews() {
		return totalTfReviews;
	}

	public void setTotalTfReviews(int totalTfReviews) {
		this.totalTfReviews = totalTfReviews;
	}
	
	@Min( value = 0 )
	@Column(name = "dd_training_in_hours")	
	public int getDdTrainingInHours() {
		return ddTrainingInHours;
	}

	public void setDdTrainingInHours(int ddTrainingInHours) {
		this.ddTrainingInHours = ddTrainingInHours;
	}
	@Min( value = 0 )
	@Column(name = "total_dd_reviews")	
	public int getTotalDdReviews() {
		return totalDdReviews;
	}

	public void setTotalDdReviews(int totalDdReviews) {
		this.totalDdReviews = totalDdReviews;
	}
	
	@Column(name = "research_development_experience", scale = 1, precision = 2)
	public double getResearchDevelopmentExperienceInYears() {
		return researchDevelopmentExperienceInYears;
	}

	public void setResearchDevelopmentExperienceInYears(
			double researchDevelopmentExperienceInYears) {
		this.researchDevelopmentExperienceInYears = researchDevelopmentExperienceInYears;
	}
	
	@OneToMany(mappedBy = "auditor", orphanRemoval = true, fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<SpecialTraining> getSpecialTrainings() {
		return specialTrainings;
	}

	public void setSpecialTrainings(Set<SpecialTraining> specialTrainings) {
		this.specialTrainings = specialTrainings;
	}
	
	
	@Length(max = 100, message = "{error.auditor.otherEmails}" )	
	@Column(length = 100, name = "other_emails")
	public String getOtherEmails() {
		return otherEmails;
	}

	public void setOtherEmails(String otherEmails) {
		this.otherEmails = otherEmails;
	}

	@Column(name = "ra_approved_for_qs_auditor")
	public boolean isRecentActivitiesApprovedForQsAuditor() {
		return recentActivitiesApprovedForQsAuditor;
	}

	public void setRecentActivitiesApprovedForQsAuditor(
			boolean recentActivitiesApprovedForQsAuditor) {
		this.recentActivitiesApprovedForQsAuditor = recentActivitiesApprovedForQsAuditor;
	}

	@Column(name = "ra_approved_for_product_assessor_a")
	public boolean isRecentActivitiesApprovedForProductAssessorA() {
		return recentActivitiesApprovedForProductAssessorA;
	}

	public void setRecentActivitiesApprovedForProductAssessorA(
			boolean recentActivitiesApprovedForProductAssessorA) {
		this.recentActivitiesApprovedForProductAssessorA = recentActivitiesApprovedForProductAssessorA;
	}

	@Column(name = "ra_approved_for_product_assessor_r")
	public boolean isRecentActivitiesApprovedForProductAssessorR() {
		return recentActivitiesApprovedForProductAssessorR;
	}

	public void setRecentActivitiesApprovedForProductAssessorR(
			boolean recentActivitiesApprovedForProductAssessorR) {
		this.recentActivitiesApprovedForProductAssessorR = recentActivitiesApprovedForProductAssessorR;
	}
	@Column(name = "ra_approved_for_product_specialist")
	public boolean isRecentActivitiesApprovedForProductSpecialist() {
		return recentActivitiesApprovedForProductSpecialist;
	}

	public void setRecentActivitiesApprovedForProductSpecialist(
			boolean recentActivitiesApprovedForProductSpecialist) {
		this.recentActivitiesApprovedForProductSpecialist = recentActivitiesApprovedForProductSpecialist;
	}
	
	@Column(name = "rating", scale = 1, precision = 1)
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	@Min(value = 0) 
	@Column(name = "audit_days_in_recent_year")
	public int getAuditDaysInRecentYear() {
		return auditDaysInRecentYear;
	}

	public void setAuditDaysInRecentYear(int auditDaysInRecentYear) {
		this.auditDaysInRecentYear = auditDaysInRecentYear;
	}
	@Min(value = 0) 
	@Column(name = "training_hours_in_recent_year")
	public int getTrainingHoursInRecentYear() {
		return trainingHoursInRecentYear;
	}

	public void setTrainingHoursInRecentYear(int trainingHoursInRecentYear) {
		this.trainingHoursInRecentYear = trainingHoursInRecentYear;
	}

	@Transient
	public boolean isIntenalAuditor(){
		return getItcId() < 1000;
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
	
	@Transient
	public String getNameWithDegree() {
		String name = getName();
		return name += StringUtils.isNotBlank(degrees) ? ", " + degrees : "";
	};
		
	
	@Transient
	public void incrementAuditDays(int plusDays){
		totalAuditdays += plusDays;
	}
	
	@Transient
	public void incrementAudits(int plusAudits){
		totalAudits += plusAudits;
	}
	
	@Transient
	public void incrementTfReviews(int plusTfReviews){
		totalTfReviews += plusTfReviews;
	}
	
	@Transient
	public void incrementDdReviews(int plusDdReviews){
		totalDdReviews += plusDdReviews;
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
		setAuditDaysInRecentYear(form.getAuditDaysInRecentYear());
		setTrainingHoursInRecentYear(form.getTrainingHoursInRecentYear());
		this.aprovedForIso9001 = form.isAprovedForIso9001();
		this.aprovedForIso13485 = form.isAprovedForIso13485();
		this.setTrainingIso9001InHours(form.getTrainingIso9001InHours());
		this.iso13485 = form.getIso13485();
		this.mdd = form.getMdd();
		this.ivd = form.getIvd();
		this.setAimd(form.getAimd());
		this.itcId = form.getItcId();
		this.nb1023Procedures = form.getNb1023Procedures();
		this.totalAudits = form.getTotalAudits();
		this.totalAuditdays = form.getTotalAuditdays();
		this.tfTrainingInHours = form.getTfTrainingInHours();
		this.totalTfReviews = form.getTotalTfReviews();		
		this.ddTrainingInHours = form.getDdTrainingInHours();
		this.totalDdReviews = form.getTotalDdReviews();
		this.otherEmails = form.getOtherEmails();
		this.rating = form.getRating();
	}
}
