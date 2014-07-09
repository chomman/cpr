package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cz.nlfnorm.quasar.views.NandoCodeType;

/**
 * 
 * Entity of Quality system assesmet reporting system, represents NANDO code
 *
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@SequenceGenerator(name = "quasar_nando_code_id_seq", sequenceName = "quasar_nando_code_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "quasar_nando_code")
public class NandoCode extends BaseEntity implements NandoCodeType{
	
	private static final long serialVersionUID = 1388976314109073881L;
	
	private String specification;
	@JsonIgnore
	private Set<NandoCode> children;
	@JsonIgnore
	private NandoCode parent;
	private Integer order;
	
	@JsonIgnore
	private boolean forProductAssesorA;
	/**
	 * Condition for auditor type: Product Assesor-A
	 * Category-specific training (hours). Default 8
	 */
	@JsonIgnore
	private Integer assesorATrainingThreashold = 8;
	/**
	 * Condition for auditor type: Product Assesor-A
	 * Number of NB audits in category. Default 1
	 */
	@JsonIgnore
	private Integer assesorANbAuditsThreashold = 1;
	/**
	 * Condition for auditor type: Product Assesor-A
	 * Number of ISO 13485 audits in relevant technical area.
	 */
	@JsonIgnore
	private Integer assesorAIso13485Threashold = 3;
	
	
	@JsonIgnore
	private boolean forProductAssesorR;
	/**
	 * Condition for auditor type: Product Assesor-R
	 * Category-specific training (hours). Default 16
	 */
	@JsonIgnore
	private Integer assesorRTrainingThreashold = 16;
	/**
	 * Condition for auditor type: Product Assesor-R
	 * No. of TF reviews in category. Default 1
	 */
	@JsonIgnore
	private Integer assesorRTFReviewsThreashold = 1;
	/**
	 * Condition for auditor type: Product Assesor-R
	 * If No. of TF reviews is bigger || equals this val, Training is complied.
	 */
	@JsonIgnore
	private Integer assesorRTFReviewsThreasholdForTraining = 3;
	
	
	@JsonIgnore
	private boolean forProductSpecialist;
	/**
	 * Condition for auditor type: Product Assesor-R
	 * Category-specific training (hours). Default 16
	 */
	@JsonIgnore
	private Integer specialistTrainingThreashold = 16;
	/**
	 * Condition for auditor type: Product Assesor-R
	 * No. of TF reviews in category. Default 1
	 */
	@JsonIgnore
	private Integer specialistDDReviewsThreashold = 1;
	/**
	 * Condition for auditor type: Product Assesor-R
	 * If No. of TF reviews is bigger || equals this val, Training is complied.
	 */
	@JsonIgnore
	private Integer specialistDDReviewsThreasholdForTraining = 5;
	
	public NandoCode(){
		this(null);
	}
	
	public NandoCode(NandoCode parent){
		this.parent = parent;
		this.children = new HashSet<>();
		this.order = 0;
		if(parent != null){
			registerInParentsChilds();
		}
	}
	
	@Override
	@Pattern(regexp = "^(MD|AIMD|MDS|IVD)\\s\\d{4}$", message = "{error.nandoCode.code.invalid}")
	@Column(length = 9, name = "code")
	public String getCode() {
		return super.getCode();
	}
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_nando_code_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Length(max = 200, message = "{error.nandoCode.specification}")
	@Column( name = "specification", length = 200 )
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	
	
	/**
	 * Second level NANDO code
	 * @return set of second level NANDO codes of this first level
	 */
	@OrderBy(clause = "order" )
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<NandoCode> getChildren() {
		return children;
	}
	

	public void setChildren(Set<NandoCode> children) {
		this.children = children;
	}
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "parent_id") 
	public NandoCode getParent() {
		return parent;
	}
	public void setParent(NandoCode parent) {
		this.parent = parent;
	}
		
	@Column(name = "nando_order")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(name = "is_for_product_assesor_a")
	public boolean isForProductAssesorA() {
		return forProductAssesorA;
	}

	public void setForProductAssesorA(boolean forProductAssesorA) {
		this.forProductAssesorA = forProductAssesorA;
	}

	@Column(name = "is_for_product_assesor_r")
	public boolean isForProductAssesorR() {
		return forProductAssesorR;
	}

	public void setForProductAssesorR(boolean forProductAssesorR) {
		this.forProductAssesorR = forProductAssesorR;
	}

	@Column(name = "is_for_product_specialist")
	public boolean isForProductSpecialist() {
		return forProductSpecialist;
	}

	public void setForProductSpecialist(boolean forProductSpecialist) {
		this.forProductSpecialist = forProductSpecialist;
	}

	
	
	@Column(name = "threashold_assesor_a_training")
	public Integer getAssesorATrainingThreashold() {
		return assesorATrainingThreashold;
	}
	
	public void setAssesorATrainingThreashold(Integer assesorATrainingThreashold) {
		this.assesorATrainingThreashold = assesorATrainingThreashold;
	}
	
	@Column(name = "threashold_assesor_a_nb_adusits")
	public Integer getAssesorANbAuditsThreashold() {
		return assesorANbAuditsThreashold;
	}

	public void setAssesorANbAuditsThreashold(Integer assesorANbAuditsThreashold) {
		this.assesorANbAuditsThreashold = assesorANbAuditsThreashold;
	}
	
	@Column(name = "threashold_assesor_a_iso_adusits")
	public Integer getAssesorAIso13485Threashold() {
		return assesorAIso13485Threashold;
	}

	public void setAssesorAIso13485Threashold(Integer assesorAIso13485Threashold) {
		this.assesorAIso13485Threashold = assesorAIso13485Threashold;
	}
	
	@Column(name = "threashold_assesor_r_training")
	public Integer getAssesorRTrainingThreashold() {
		return assesorRTrainingThreashold;
	}

	public void setAssesorRTrainingThreashold(Integer assesorRTrainingThreashold) {
		this.assesorRTrainingThreashold = assesorRTrainingThreashold;
	}
	
	@Column(name = "threashold_assesor_r_tf_reviews")
	public Integer getAssesorRTFReviewsThreashold() {
		return assesorRTFReviewsThreashold;
	}

	public void setAssesorRTFReviewsThreashold(Integer assesorRTFReviewsThreashold) {
		this.assesorRTFReviewsThreashold = assesorRTFReviewsThreashold;
	}

	@Column(name = "threashold_assesor_r_tf_reviews_for_training")
	public Integer getAssesorRTFReviewsThreasholdForTraining() {
		return assesorRTFReviewsThreasholdForTraining;
	}

	public void setAssesorRTFReviewsThreasholdForTraining(
			Integer assesorRTFReviewsThreasholdForTraining) {
		this.assesorRTFReviewsThreasholdForTraining = assesorRTFReviewsThreasholdForTraining;
	}
	
	@Column(name = "threashold_specialist_training")
	public Integer getSpecialistTrainingThreashold() {
		return specialistTrainingThreashold;
	}

	public void setSpecialistTrainingThreashold(Integer specialistTrainingThreashold) {
		this.specialistTrainingThreashold = specialistTrainingThreashold;
	}
	
	@Column(name = "threashold_specialist_dd_reviews")
	public Integer getSpecialistDDReviewsThreashold() {
		return specialistDDReviewsThreashold;
	}

	public void setSpecialistDDReviewsThreashold(
			Integer specialistDDReviewsThreashold) {
		this.specialistDDReviewsThreashold = specialistDDReviewsThreashold;
	}
	
	@Column(name = "threashold_specialist_dd_reviews_for_training")
	public Integer getSpecialistDDReviewsThreasholdForTraining() {
		return specialistDDReviewsThreasholdForTraining;
	}

	public void setSpecialistDDReviewsThreasholdForTraining(
			Integer specialistDDReviewsThreasholdForTraining) {
		this.specialistDDReviewsThreasholdForTraining = specialistDDReviewsThreasholdForTraining;
	}

	
	private void registerInParentsChilds() {
		this.parent.children.add(this);
	}
	
	/**
	 * Returns TRUE, if is this code for active medical device  
	 * Active MD has code MD 1000 and higher
	 * 
	 * @return TRUE, if is active MD
	 */
	@Override
	@Transient
	public boolean isActiveMd(){
		return (getCode() != null && getCode().startsWith("MD 1"));
	}
	
	/**
	 * Returns TRUE, if is this code for NON-Active medical device  
	 * NON Active MD has code MD up to MD 0999
	 * 
	 * @return TRUE, if is NON-Active MD
	 */
	@Override
	@Transient
	public boolean isNonActiveMd(){
		return (getCode() != null && getCode().startsWith("MD 0"));
	}
	
	@Override
	@Transient
	public boolean isIvd(){
		return (getCode() != null && (getCode().startsWith("IVD") || getCode().startsWith("MDS 72")));
	}

	@Override
	@Transient
	public boolean isHorizontal() {
		return (getCode() != null && getCode().startsWith("MDS 70"));
	}
}
