package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;


@Entity
@Table(name = "quasar_settings")
public class QuasarSettings implements Serializable{

	public static final long SETTINGS_ID = 1l;
	
	private static final long serialVersionUID = 8185849689931323767L;

	private Long id;
	
	/* Qs Auditor settings */
	private int qsAuditorMdTraining;
	private int qsAuditorIso13485Training;
	private int qsAuditorClassRoomTraining;	
	private int qsAuditorNb1023Procedures;
	private int qsAuditorNoAudits;
	private int minQsAuditorAuditDaysInRecentYear;
	
	/* Product Assessor-A settings */
	private int productAssessorAMdTraining;
	private int productAssessorAIvdTraining;
	private int productAssessorANb1023Procedures;
	private int productAssessorANoAudits;
	private int minProductAssessorAAuditDaysInRecentYear;
	
	/* Product Assessor-R settings */
	private int productAssessorRMdTraining;
	private int productAssessorRIvdTraining;
	private int productAssessorRTfTrainingReview;
	private int productAssessorRTfTotal;
	
	private int minTfReviewsInRecentYear;
	private int minTfReviewsInRecentThreeYears;
	
	/* Product Specialist settings */
	private int productSpecialistMdTraining;
	private int productSpecialistIvdTraining;
	private int productSpecialistDdTrainingReview;
	private int productSpecialistDdTotal;
	
	private int minDdReviewsInRecentYear;
	private int minDdReviewsInRecentThreeYears;
	
	/* Recent activities */
	private int minTrainingHoursInRecentYear;
	
	private String notificationEmail;
	
	/**
	 * Setting for recent activities query.Consider that today's date is 22.07.2014.
	 * If the the value is TRUE SQL clause will be following: 
	 * <code>WHERE log.auditDate >= 22.07.2013</code>
	 * 
	 * If the the value is FALSE SQL clause will be following: 
	 * <code>WHERE log.auditDate >= 01.01.2013</code>
	 * resp. date to 01.01.yyyy, where yyyy is current year - 1
	 * 
	 * @see {@link AbstractLog} - auditDate property
	 */
	private boolean use365DaysInterval;
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Min(value = 0 )
	@Column(name = "qs_auditor_md_training")
	public int getQsAuditorMdTraining() {
		return qsAuditorMdTraining;
	}
	public void setQsAuditorMdTraining(int qsAuditorMdTraining) {
		this.qsAuditorMdTraining = qsAuditorMdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "qs_auditor_iso13485_training")	
	public int getQsAuditorIso13485Training() {
		return qsAuditorIso13485Training;
	}
	public void setQsAuditorIso13485Training(int qsAuditorIso13485Training) {
		this.qsAuditorIso13485Training = qsAuditorIso13485Training;
	}
	
	@Min(value = 0 )
	@Column(name = "qs_auditor_class_room_training")
	public int getQsAuditorClassRoomTraining() {
		return qsAuditorClassRoomTraining;
	}
	public void setQsAuditorClassRoomTraining(int qsAuditorClassRoomTraining) {
		this.qsAuditorClassRoomTraining = qsAuditorClassRoomTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "qs_auditor_nb1023_procedures")
	public int getQsAuditorNb1023Procedures() {
		return qsAuditorNb1023Procedures;
	}
	public void setQsAuditorNb1023Procedures(int qsAuditorNb1023Procedures) {
		this.qsAuditorNb1023Procedures = qsAuditorNb1023Procedures;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_a_md_training")
	public int getProductAssessorAMdTraining() {
		return productAssessorAMdTraining;
	}
	public void setProductAssessorAMdTraining(int productAssessorAMdTraining) {
		this.productAssessorAMdTraining = productAssessorAMdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_a_ivd_training")
	public int getProductAssessorAIvdTraining() {
		return productAssessorAIvdTraining;
	}
	public void setProductAssessorAIvdTraining(int productAssessorAIvdTraining) {
		this.productAssessorAIvdTraining = productAssessorAIvdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_a_nb1023_procedures")
	public int getProductAssessorANb1023Procedures() {
		return productAssessorANb1023Procedures;
	}
	public void setProductAssessorANb1023Procedures(int productAssessorANb1023Procedures) {
		this.productAssessorANb1023Procedures = productAssessorANb1023Procedures;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_r_md_training")
	public int getProductAssessorRMdTraining() {
		return productAssessorRMdTraining;
	}
	public void setProductAssessorRMdTraining(int productAssessorRMdTraining) {
		this.productAssessorRMdTraining = productAssessorRMdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_r_ivd_training")
	public int getProductAssessorRIvdTraining() {
		return productAssessorRIvdTraining;
	}
	public void setProductAssessorRIvdTraining(int productAssessorRIvdTraining) {
		this.productAssessorRIvdTraining = productAssessorRIvdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_r_tf_training_review")
	public int getProductAssessorRTfTrainingReview() {
		return productAssessorRTfTrainingReview;
	}
	public void setProductAssessorRTfTrainingReview(
			int productAssessorRTfTrainingReview) {
		this.productAssessorRTfTrainingReview = productAssessorRTfTrainingReview;
	}
	
	@Min(value = 0 )
	@Column(name = "product_assessor_r_tf_total")
	public int getProductAssessorRTfTotal() {
		return productAssessorRTfTotal;
	}
	public void setProductAssessorRTfTotal(int productAssessorRTfTotal) {
		this.productAssessorRTfTotal = productAssessorRTfTotal;
	}
	
	@Min(value = 0 )
	@Column(name = "product_specialist_md_training")
	public int getProductSpecialistMdTraining() {
		return productSpecialistMdTraining;
	}
	public void setProductSpecialistMdTraining(int productSpecialistMdTraining) {
		this.productSpecialistMdTraining = productSpecialistMdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "product_specialist_ivd_training")
	public int getProductSpecialistIvdTraining() {
		return productSpecialistIvdTraining;
	}
	public void setProductSpecialistIvdTraining(int productSpecialistIvdTraining) {
		this.productSpecialistIvdTraining = productSpecialistIvdTraining;
	}
	
	@Min(value = 0 )
	@Column(name = "product_specialist_dd_training_review")
	public int getProductSpecialistDdTrainingReview() {
		return productSpecialistDdTrainingReview;
	}
	public void setProductSpecialistDdTrainingReview(int productSpecialistDdTrainingReview) {
		this.productSpecialistDdTrainingReview = productSpecialistDdTrainingReview;
	}
	
	@Min(value = 0 )
	@Column(name = "product_specialist_dd_total")
	public int getProductSpecialistDdTotal() {
		return productSpecialistDdTotal;
	}
	public void setProductSpecialistDdTotal(int productSpecialistDdTotal) {
		this.productSpecialistDdTotal = productSpecialistDdTotal;
	}
	
	@Min(value = 0 )
	@Column(name = "qs_auditor_no_audits")
	public int getQsAuditorNoAudits() {
		return qsAuditorNoAudits;
	}
	public void setQsAuditorNoAudits(int qsAuditorNoAudits) {
		this.qsAuditorNoAudits = qsAuditorNoAudits;
	}
	@Min(value = 0 )
	@Column(name = "product_assessor_a_no_audits")
	public int getProductAssessorANoAudits() {
		return productAssessorANoAudits;
	}
	public void setProductAssessorANoAudits(int productAssessorANoAudits) {
		this.productAssessorANoAudits = productAssessorANoAudits;
	}
	
	@Email
	@Column(name = "notification_email", length = 50)
	public String getNotificationEmail() {
		return notificationEmail;
	}
	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}
	
	@Min(value = 0)
	@Column(name = "min_qs_auditor_audit_days_in_recent_year")
	public int getMinQsAuditorAuditDaysInRecentYear() {
		return minQsAuditorAuditDaysInRecentYear;
	}
	public void setMinQsAuditorAuditDaysInRecentYear(int minAuditsInRecentYear) {
		this.minQsAuditorAuditDaysInRecentYear = minAuditsInRecentYear;
	}
		
	@Min(value = 0)
	@Column(name = "min_product_assessor_a_audit_days_in_recent_year")
	public int getMinProductAssessorAAuditDaysInRecentYear() {
		return minProductAssessorAAuditDaysInRecentYear;
	}
	public void setMinProductAssessorAAuditDaysInRecentYear(
			int minProductAssessorAAuditDaysInRecentYear) {
		this.minProductAssessorAAuditDaysInRecentYear = minProductAssessorAAuditDaysInRecentYear;
	}
	@Min(value = 0)
	@Column(name = "min_training_hours_in_recent_year")
	public int getMinTrainingHoursInRecentYear() {
		return minTrainingHoursInRecentYear;
	}
	public void setMinTrainingHoursInRecentYear(int minTrainingHoursInRecentYear) {
		this.minTrainingHoursInRecentYear = minTrainingHoursInRecentYear;
	}
	
	@Column(name = "use_365_days_interval")
	public boolean isUse365DaysInterval() {
		return use365DaysInterval;
	}
	public void setUse365DaysInterval(boolean use365DaysInterval) {
		this.use365DaysInterval = use365DaysInterval;
	}
	
	@Min(value = 0)
	@Column(name = "min_tf_reviews_in_recent_year")
	public int getMinTfReviewsInRecentYear() {
		return minTfReviewsInRecentYear;
	}
	public void setMinTfReviewsInRecentYear(int minTfReviewsInRecentYear) {
		this.minTfReviewsInRecentYear = minTfReviewsInRecentYear;
	}
	
	@Min(value = 0)
	@Column(name = "min_tf_reviews_in_recent_tree_years")
	public int getMinTfReviewsInRecentThreeYears() {
		return minTfReviewsInRecentThreeYears;
	}
	public void setMinTfReviewsInRecentThreeYears(int minTfReviewsInRecentThreeYears) {
		this.minTfReviewsInRecentThreeYears = minTfReviewsInRecentThreeYears;
	}
	
	@Min(value = 0)
	@Column(name = "min_dd_reviews_in_recent_year")
	public int getMinDdReviewsInRecentYear() {
		return minDdReviewsInRecentYear;
	}
	public void setMinDdReviewsInRecentYear(int minDdReviewsInRecentYear) {
		this.minDdReviewsInRecentYear = minDdReviewsInRecentYear;
	}
	
	@Min(value = 0)
	@Column(name = "min_dd_reviews_in_recent_tree_years")
	public int getMinDdReviewsInRecentThreeYears() {
		return minDdReviewsInRecentThreeYears;
	}
	public void setMinDdReviewsInRecentThreeYears(int minDdReviewsInRecentThreeYears) {
		this.minDdReviewsInRecentThreeYears = minDdReviewsInRecentThreeYears;
	}
	
	
	
	
	
}
