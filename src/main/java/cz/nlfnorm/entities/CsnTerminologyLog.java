package cz.nlfnorm.entities;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import cz.nlfnorm.enums.ImportStatus;


@Entity
@SequenceGenerator(name = "csn_terminology_log_id_seq", sequenceName = "csn_terminology_log_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "csn_terminology_log")
public class CsnTerminologyLog {
	
	private Long id;
	
	private LocalDateTime created;
	
	private User createdBy;
	
	private Boolean success = false;
	
	private String description = "";
	
	private int czCount = 0;
	
	private int enCount = 0;
	
	private long duration;
	
	private String fileName;
	
	private int imageCount = 0;
	
	private Csn csn;
	
	private ImportStatus importStatus;
	
	public CsnTerminologyLog(){
		importStatus = ImportStatus.FAILED;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "csn_terminology_log_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	@Type(type = "text")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "cz_count")
	public int getCzCount() {
		return czCount;
	}

	public void setCzCount(int czCount) {
		this.czCount = czCount;
	}
	
	@Column(name = "en_count")
	public int getEnCount() {
		return enCount;
	}

	public void setEnCount(int enCount) {
		this.enCount = enCount;
	}
	

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	@Column(name = "file_name", length = 30)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name = "count_of_images")
	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name="import_status", length = 15)
	public ImportStatus getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(ImportStatus importStatus) {
		this.importStatus = importStatus;
	}

	@Transient
	public void logInfo(String message){
		this.description += "<p class=\"tlog info\">" + message + "</p>";
	}
	
	@Transient
	public void logAlert(String message){
		this.description += "<p class=\"tlog alert\">" + message + "</p>";
	}
	
	@Transient
	public void logError(String message){
		this.description += "<p class=\"tlog error\">" + message + "</p>";
	}
	
	@Transient
	public void incrementCZ(){
		this.czCount++;
	}
	
	@Transient
	public void incrementEN(){
		this.enCount++;
	}
	
	@Transient
	public void incremetImageCount(){
		this.imageCount++;
	}
	
	@Transient
	public void updateImportStatus(){
		if(czCount == 0 && enCount == 0){
			importStatus = ImportStatus.FAILED;
		}else if(czCount != enCount && enCount > 0){
			importStatus = ImportStatus.INCOMPLETE;
		}else if((czCount > 0 && czCount == enCount) || czCount > 0 && enCount == 0){
			importStatus = ImportStatus.SUCCESS;
		}else{
			importStatus = ImportStatus.FAILED;
		}
	}
	
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csn_id")
	public Csn getCsn() {
		return csn;
	}

	
	public void setCsn(Csn csn) {
		this.csn = csn;
	}
	
	
}
