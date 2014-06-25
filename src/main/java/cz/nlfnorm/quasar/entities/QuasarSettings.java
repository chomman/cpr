package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;


@Entity
@Table(name = "quasar_settings")
public class QuasarSettings implements Serializable{

	public static final long SETTINGS_ID = 1l;
	
	private static final long serialVersionUID = 8185849689931323767L;

	private Long id;
	
	/* Training */
	private int qsAuditorMdTraining;
	private int qsAuditorIso13485Training;
	private int qsAuditorClassRoomTraining;	
	private int qsAuditorNb1023Procedures;
	private int qsAuditorNoAudits;
	
	/* Training */
	private int productAssessorAMdTraining;
	private int productAssessorAIvdTraining;
	private int productAssessorANb1023Procedures;
	private int productAssessorANoAudits;
	
	/* Training */
	private int productAssessorRMdTraining;
	private int productAssessorRIvdTraining;

	private int productAssessorRTfTrainingReview;
	private int productAssessorRTfTotal;
	
	
	/* Training */
	private int productSpecialistMdTraining;
	private int productSpecialistIvdTraining;
	
	private int productSpecialistDdTrainingReview;
	private int productSpecialistDdTotal;
	
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
	
	
	
	
}
