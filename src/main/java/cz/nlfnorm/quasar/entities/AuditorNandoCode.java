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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import cz.nlfnorm.entities.NotifiedBody;

/**
 * QUASAR entity
 * Represents Auditor's NANDO code
 * 
 * @author Peter Jurkovic
 * @date Jun 15, 2014
 */
@Entity
@Table(name = "quasar_auditor_has_nando_code", uniqueConstraints = @UniqueConstraint(columnNames = {"nando_code_id", "auditor_id"}) )
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "quasar_auditor_has_nando_code_id_seq", sequenceName = "quasar_auditor_has_nando_code_id_seq", initialValue = 1, allocationSize =1)
public class AuditorNandoCode extends AbstractAuditorCode {

	private static final long serialVersionUID = -4193813519262412701L;
	
	
	/* Product Assessor A attirbutes */
	/**
	 * Category-specific training (hours)
	 */
	private int productAssessorATraining = 0;
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
	 * Reason details description
	 */
	private String productAssessorAReasonDetails;
	/**
	 * Category approval by another NB
	 */
	private NotifiedBody productAssessorAApprovedBy;
	
	
	
	
	/* Product Assessor R attirbutes */
	/**
	 * Category-specific training (hours)
	 */
	private int productAssessorRTraining = 0;
	/**
	 * No. of TF reviews in category.
	 */
	private int numberOfTfReviews = 0;
	/**
	 * Specific reason for the approval (in case of missing audits)
	 */
	private boolean productAssessorRApproved;
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
	 * Category-specific training (hours)
	 */
	private int productSpecialistTraining = 0;
	/**
	 * No. of TF reviews in category.
	 */
	private int numberOfDdReviews = 0;
	/**
	 * Specific reason for the approval (in case of missing audits)
	 */
	private boolean productSpecialistApproved;
	/**
	 * Reason details description
	 */
	private String productSpecialistReasonDetails;
	/**
	 * Category approval by another NB
	 */
	private NotifiedBody productSpecialistApprovedBy;
	
	
	
	private NandoCode nandoCode;
	
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
	 * @return Product Assessor-A category-specific training in hours
	 */
	@Min(value = 0)
	@Column(name = "product_assessor_a_training")	
	public int getProductAssessorATraining() {
		return productAssessorATraining;
	}


	public void setProductAssessorATraining(int productAssessorATraining) {
		this.productAssessorATraining = productAssessorATraining;
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
	@Column(name = "product_assessor_r_training")	
	public int getProductAssessorRTraining() {
		return productAssessorRTraining;
	}


	public void setProductAssessorRTraining(int productAssessorRTraining) {
		this.productAssessorRTraining = productAssessorRTraining;
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
	 * @return Product Specialist category-specific training in hours
	 */
	@Min(value = 0)
	@Column(name = "product_specialist_training")	
	public int getProductSpecialistTraining() {
		return productSpecialistTraining;
	}


	public void setProductSpecialistTraining(int productSpecialistTraining) {
		this.productSpecialistTraining = productSpecialistTraining;
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


	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "nando_code_id")
	public NandoCode getNandoCode() {
		return nandoCode;
	}
	public void setNandoCode(NandoCode nandoCode) {
		this.nandoCode = nandoCode;
	}
	
	
}
