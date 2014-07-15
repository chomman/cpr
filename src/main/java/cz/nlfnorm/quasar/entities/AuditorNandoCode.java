package cz.nlfnorm.quasar.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.entities.NotifiedBody;
import cz.nlfnorm.quasar.views.NandoCodeType;

/**
 * QUASAR entity
 * Represents Auditor's NANDO code
 * 
 * @author Peter Jurkovic
 * @date Jun 15, 2014
 */
@Entity
@Table( name = "quasar_auditor_has_nando_code", uniqueConstraints = @UniqueConstraint(columnNames = {"nando_code_id", "auditor_id"}) )
@SequenceGenerator(name = "quasar_auditor_has_nando_code_id_seq", sequenceName = "quasar_auditor_has_nando_code_id_seq", initialValue = 1, allocationSize =1)
public class AuditorNandoCode extends AbstractAuditorCode implements NandoCodeType{

	private static final long serialVersionUID = -4193813519262412701L;
	
	
	/* Product Assessor A attirbutes */
	/**
	 * Number of NB audits in category
	 */
	private int numberOfNbAudits = 0;
	/**
	 * Number of ISO 13485 audits in relevant technical area
	 */
	private int numberOfIso13485Audits = 0;
	/**
	 * Specific reason for the approval (in case of missing audits)
	 */
	private boolean productAssessorAApproved;
	/**
	 * Rejected for given NANDO CODE
	 */
	private boolean productAssessorARefused;
	/**
	 * Reason details description
	 */
	private String productAssessorAReasonDetails;
	/**
	 * Category approval by another NB
	 */
	private NotifiedBody productAssessorAApprovedBy;
	
		
	
	/**
	 * Category-specific training (hours)
	 */
	private int categorySpecificTraining = 0;
	
	private int childrenCategorySpecificTraining;
	
	
	/* Product Assessor R attirbutes */
	/**
	 * No. of TF reviews in category.
	 */
	private int numberOfTfReviews = 0;
	/**
	 * Specific reason for the approval (in case of missing audits)
	 */
	private boolean productAssessorRApproved;
	/**
	 * Rejected for given NANDO CODE
	 */
	private boolean productAssessorRRefused;
	/**
	 * Reason details description
	 */
	private String productAssessorRReasonDetails;
	/**
	 * Category approval by another NB
	 */
	private NotifiedBody productAssessorRApprovedBy;
	
	
	
	
	/* Product Specialist attirbutes */
	/**
	 * No. of TF reviews in category.
	 */
	private int numberOfDdReviews = 0;
	/**
	 * Specific reason for the approval (in case of missing audits)
	 */
	private boolean productSpecialistApproved;
	/**
	 * Rejected for given NANDO CODE
	 */
	private boolean productSpecialistRefused;
	/**
	 * Reason details description
	 */
	private String productSpecialistReasonDetails;
	/**
	 * Category approval by another NB
	 */
	private NotifiedBody productSpecialistApprovedBy;
	
	private NandoCode nandoCode;
	
	public AuditorNandoCode(){}
	public AuditorNandoCode(Auditor auditor, NandoCode code){
		setAuditor(auditor);
		setNandoCode(code);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_auditor_has_nando_code_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
		
	@Min(value = 0)
	@Column(name = "number_of_nb_audits")
	public int getNumberOfNbAudits() {
		return numberOfNbAudits;
	}
	public void setNumberOfNbAudits(int numberOfNbAudits) {
		this.numberOfNbAudits = numberOfNbAudits;
	}
	
	@Min(value = 0)
	@Column(name = "number_of_iso13485_audits")
	public int getNumberOfIso13485Audits() {
		return numberOfIso13485Audits;
	}
	public void setNumberOfIso13485Audits(int numberOfIso13485Audits) {
		this.numberOfIso13485Audits = numberOfIso13485Audits;
	}
	

	/**
	 * @return TRUE if has Product Assessor-A specific approval
	 */
	@Column( name = "product_assessor_a_approved")
	public boolean isProductAssessorAApproved() {
		return productAssessorAApproved;
	}


	public void setProductAssessorAApproved(boolean productAssessorAApproved) {
		this.productAssessorAApproved = productAssessorAApproved;
	}

	@Length(max = 255, message = "{error.auditorNandoCode.reason.lenght}")
	@Column( name = "product_assessor_a_reason", length = 255)
	public String getProductAssessorAReasonDetails() {
		return productAssessorAReasonDetails;
	}


	public void setProductAssessorAReasonDetails(String productAssessorAReasonDetails) {
		this.productAssessorAReasonDetails = productAssessorAReasonDetails;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_assessor_a_notified_body_id")
	public NotifiedBody getProductAssessorAApprovedBy() {
		return productAssessorAApprovedBy;
	}


	public void setProductAssessorAApprovedBy(NotifiedBody notifiedBody) {
		this.productAssessorAApprovedBy = notifiedBody;
	}

	
	/**
	 * @return Product Assessor-R category-specific training in hours
	 */
	@Min(value = 0)
	@Column(name = "category_specific_training")	
	public int getCategorySpecificTraining() {
		return categorySpecificTraining;
	}


	public void setCategorySpecificTraining(int categorySpecificTraining) {
		this.categorySpecificTraining = categorySpecificTraining;
	}

	/**
	 * @return No. of TF reviews in category
	 */
	@Min(value = 0)
	@Column(name = "number_of_tf_reviews", nullable = false)
	public int getNumberOfTfReviews() {
		return numberOfTfReviews;
	}


	public void setNumberOfTfReviews(int numberOfTfReviews) {
		this.numberOfTfReviews = numberOfTfReviews;
	}

	/**
	 * @return TRUE if has Product Assessor-R specific approval
	 */
	@Column( name = "product_assessor_r_approved")
	public boolean isProductAssessorRApproved() {
		return productAssessorRApproved;
	}


	public void setProductAssessorRApproved(boolean productAssessorRApproved) {
		this.productAssessorRApproved = productAssessorRApproved;
	}

	@Length(max = 255, message = "{error.auditorNandoCode.reason.lenght}")
	@Column( name = "product_assessor_r_reason", length = 255)
	public String getProductAssessorRReasonDetails() {
		return productAssessorRReasonDetails;
	}


	public void setProductAssessorRReasonDetails(String productAssessorRReasonDetails) {
		this.productAssessorRReasonDetails = productAssessorRReasonDetails;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_assessor_r_notified_body_id")
	public NotifiedBody getProductAssessorRApprovedBy() {
		return productAssessorRApprovedBy;
	}


	public void setProductAssessorRApprovedBy(NotifiedBody notifiedBody) {
		this.productAssessorRApprovedBy = notifiedBody;
	}


	/**
	 * @return No. of design dosire reviews in category
	 */
	@Min(value = 0)
	@Column(name = "number_of_dd_reviews", nullable = false)
	public int getNumberOfDdReviews() {
		return numberOfDdReviews;
	}


	public void setNumberOfDdReviews(int numberOfDdReviews) {
		this.numberOfDdReviews = numberOfDdReviews;
	}

	/**
	 * @return TRUE if has Product Specialist specific approval
	 */
	@Column( name = "product_specialist_approved")
	public boolean isProductSpecialistApproved() {
		return productSpecialistApproved;
	}


	public void setProductSpecialistApproved(boolean productSpecialistApproved) {
		this.productSpecialistApproved = productSpecialistApproved;
	}

	@Length(max = 255, message = "{error.auditorNandoCode.reason.lenght}")
	@Column( name = "product_specialist_reason", length = 255)
	public String getProductSpecialistReasonDetails() {
		return productSpecialistReasonDetails;
	}


	public void setProductSpecialistReasonDetails(String productSpecialistReasonDetails) {
		this.productSpecialistReasonDetails = productSpecialistReasonDetails;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_specialist_notified_body_id")
	public NotifiedBody getProductSpecialistApprovedBy() {
		return productSpecialistApprovedBy;
	}
	
	public void setProductSpecialistApprovedBy(NotifiedBody notifiedBody) {
		this.productSpecialistApprovedBy = notifiedBody;
	}
	
	@Column( name = "is_product_assessor_a_refused")
	public boolean isProductAssessorARefused() {
		return productAssessorARefused;
	}
	public void setProductAssessorARefused(boolean productAssessorARefused) {
		this.productAssessorARefused = productAssessorARefused;
	}
	@Column( name = "is_product_assessor_r_refused")
	public boolean isProductAssessorRRefused() {
		return productAssessorRRefused;
	}
	public void setProductAssessorRRefused(boolean productAssessorRRefused) {
		this.productAssessorRRefused = productAssessorRRefused;
	}
	@Column( name = "is_product_specialist_refused")
	public boolean isProductSpecialistRefused() {
		return productSpecialistRefused;
	}
	public void setProductSpecialistRefused(boolean productSpecialistRefused) {
		this.productSpecialistRefused = productSpecialistRefused;
	}
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "nando_code_id")
	public NandoCode getNandoCode() {
		return nandoCode;
	}
	public void setNandoCode(NandoCode nandoCode) {
		this.nandoCode = nandoCode;
	}
	
	/**
	 * Returns sum of children category-specifict training 
	 * 
	 * @return int category specific training
	 */
	//@Transient
	@Formula(value = "(select COALESCE(sum(anc.category_specific_training),0) from quasar_auditor_has_nando_code anc "+
		            "inner join quasar_nando_code nc ON anc.nando_code_id=nc.id "+
		            "where nc.parent_id = nando_code_id and anc.auditor_id = auditor_id)")
	public int getChildrenCategorySpecificTraining() {
		return childrenCategorySpecificTraining;
	}
	public void setChildrenCategorySpecificTraining(int parentCategorySpecificTraining) {
		this.childrenCategorySpecificTraining = parentCategorySpecificTraining;
	}
	
	@Transient
	public int getTotalCategorySpecificTraining(){
		return categorySpecificTraining + getChildrenCategorySpecificTraining();
	}
	
	@Transient
	@Override
	public boolean isActiveMd() {
		return getNandoCode() != null && getNandoCode().isActiveMd();
	}
	@Transient
	@Override
	public boolean isNonActiveMd() {
		return getNandoCode() != null && getNandoCode().isNonActiveMd();
	}
	@Transient
	@Override
	public boolean isIvd() {
		return getNandoCode() != null && getNandoCode().isIvd();
	}
	
	@Transient
	public boolean isGrantedForProductAssessorA(){
		return !isProductAssessorARefused() && (
			   isProductAssessorAApproved() ||
			   getProductAssessorAApprovedBy() != null ||
			   ( 
					   getTotalCategorySpecificTraining() >= getNandoCode().getAssesorATrainingThreashold() &&
					   (
							   getNumberOfNbAudits() >= getNandoCode().getAssesorANbAuditsThreashold() ||
							   getNumberOfIso13485Audits() >= getNandoCode().getAssesorAIso13485Threashold()
					   )
			   ));
	}
	
	@Transient
	public boolean isGrantedForProductAssessorR(){
		return !isProductAssessorRRefused() && (
			   isProductAssessorRApproved() ||
			   getProductAssessorRApprovedBy() != null ||
			   ( 
					(		
						getCategorySpecificTraining() >= getNandoCode().getAssesorATrainingThreashold() ||
						getNumberOfTfReviews() >= getNandoCode().getAssesorRTFReviewsThreasholdForTraining()
					) 
					&&
					(
						getNumberOfTfReviews() >= getNandoCode().getAssesorRTFReviewsThreashold()
					)
					
			   ));
	}
	
	@Transient
	public boolean isGrantedForProductSpecialist(){
		return !isProductSpecialistRefused() && (
			   isProductSpecialistApproved() ||
			   getProductSpecialistApprovedBy() != null ||
			   ( 
					(		
						getCategorySpecificTraining() >= getNandoCode().getSpecialistTrainingThreashold() ||
						getNumberOfTfReviews() + getNumberOfDdReviews() >= getNandoCode().getSpecialistDDReviewsThreasholdForTraining()
					) 
					&&
					(
						getNumberOfDdReviews() >= getNandoCode().getSpecialistDDReviewsThreashold()
					)
			   ));
	}
	
	
	@Transient
	@Override
	public boolean isHorizontal() {
		return getNandoCode() != null && getNandoCode().isHorizontal();
	}
	
	@Transient
	public void mergeProductAssessorA(AuditorNandoCode code){
		categorySpecificTraining = code.getCategorySpecificTraining();
		numberOfNbAudits	= code.getNumberOfNbAudits();
		numberOfIso13485Audits = code.getNumberOfIso13485Audits();
		productAssessorAApproved = code.isProductAssessorAApproved();
		productAssessorARefused = code.isProductAssessorARefused();
		productAssessorAReasonDetails = code.getProductAssessorAReasonDetails();
		productAssessorAApprovedBy = code.getProductAssessorAApprovedBy();
	}
	
	@Transient
	public void mergeProductAssessorR(AuditorNandoCode code){
		categorySpecificTraining = code.getCategorySpecificTraining();
		numberOfTfReviews = code.getNumberOfTfReviews();
		productAssessorRApproved = code.isProductAssessorRApproved();
		productAssessorRRefused = code.isProductAssessorRRefused();
		productAssessorRReasonDetails = code.getProductAssessorRReasonDetails();
		productAssessorRApprovedBy = code.getProductAssessorRApprovedBy();
	}
	
	@Transient
	public void mergeProductSpecialist(AuditorNandoCode code){
		categorySpecificTraining = code.getCategorySpecificTraining();
		numberOfDdReviews = code.getNumberOfDdReviews();
		numberOfTfReviews = code.getNumberOfTfReviews();
		productSpecialistApproved = code.isProductSpecialistApproved();
		productSpecialistRefused = code.isProductSpecialistRefused();
		productSpecialistReasonDetails = code.getProductSpecialistReasonDetails();
		productSpecialistApprovedBy = code.getProductSpecialistApprovedBy();
	}
	
	@Transient
	public void incrementNumberOfNbAudits(int val){
		numberOfNbAudits += val;
	}
	
	@Transient
	public void incrementNumberOfIso13485Audits(int val){
		numberOfIso13485Audits += val;
	}
	
}
