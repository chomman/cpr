package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.apache.commons.lang.Validate;

@Entity
@Table(name = "quasar_training_log")
public class TrainingLog extends AbstractLog {
	
	private static final long serialVersionUID = 7779228785089436679L;
	
	private Set<Auditor> auditors;
	private int iso9001;
	private int iso13485;
	private int mdd;
	private int ivd;
	private int aimd;
	private int nb1023Procedures;
	private String attachment;
	private Set<CategorySpecificTraining> categorySpecificTrainings;
	
	public TrainingLog(){
		auditors = new HashSet<>();
		categorySpecificTrainings = new HashSet<>();
	}
	
	@ManyToMany
    @JoinTable(name = "quasar_training_log_has_auditors", joinColumns = @JoinColumn(name = "training_log_id"), inverseJoinColumns = @JoinColumn(name = "auditor_id") )
	public Set<Auditor> getAuditors() {
		return auditors;
	}
	public void setAuditors(Set<Auditor> auditors) {
		this.auditors = auditors;
	}
	
	@Min(value = 0)
	@Column(name = "iso_9001", columnDefinition = "SMALLINT" )
	public int getIso9001() {
		return iso9001;
	}
	public void setIso9001(int iso9001) {
		this.iso9001 = iso9001;
	}
	
	@Min(value = 0)
	@Column(name = "iso_13485", columnDefinition = "SMALLINT" )
	public int getIso13485() {
		return iso13485;
	}
	public void setIso13485(int iso13485) {
		this.iso13485 = iso13485;
	}
	
	@Min(value = 0)
	@Column(name = "mdd", columnDefinition = "SMALLINT" )
	public int getMdd() {
		return mdd;
	}
	public void setMdd(int mdd) {
		this.mdd = mdd;
	}
	
	@Min(value = 0)
	@Column(name = "ivd", columnDefinition = "SMALLINT" )
	public int getIvd() {
		return ivd;
	}
	public void setIvd(int ivd) {
		this.ivd = ivd;
	}
	
	@Min(value = 0)
	@Column(name = "aimd", columnDefinition = "SMALLINT" )
	public int getAimd() {
		return aimd;
	}
	public void setAimd(int aimd) {
		this.aimd = aimd;
	}
	
	@Min(value = 0)
	@Column(name = "nb1023_procedures", columnDefinition = "SMALLINT" )
	public int getNb1023Procedures() {
		return nb1023Procedures;
	}
	public void setNb1023Procedures(int nb1023Procedures) {
		this.nb1023Procedures = nb1023Procedures;
	}
	
	@Column(length = 32)
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@OneToMany(mappedBy = "trainingLog", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<CategorySpecificTraining> getCategorySpecificTrainings() {
		return categorySpecificTrainings;
	}
	
	public void setCategorySpecificTrainings(Set<CategorySpecificTraining> categorySpecificTrainings) {
		this.categorySpecificTrainings = categorySpecificTrainings;
	}
	
	@Transient
	public void addAuditor(final Auditor auditor){
		Validate.notNull(auditor);
		auditors.add(auditor);
	}
	
	@Transient
	public int getAuditorsSize(){
		return  auditors.size();
	}
}
