package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Iterator;
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "quasar_training_log")
public class TrainingLog extends AbstractLog {
	
	private static final long serialVersionUID = 7779228785089436679L;
	
	private LocalDate date;
	private String subject;
	
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
	
	@Column(name = "training_date")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Column
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
		if(!auditors.contains(auditor)){
			auditors.add(auditor);
		}
	}
	
	@Transient
	public boolean removeAuditor(final Long id){
		Iterator<Auditor> i = auditors.iterator();
		while (i.hasNext()) {
		   final Auditor auditor = i.next();
		   if(auditor.getId().equals(id)){
			   i.remove();
			   return true;
		   }
		}
		return false;
	}
	
	@Transient
	public int getAuditorsSize(){
		return  auditors.size();
	}
	
	/**
	 * Add given {@link CategorySpecificTraining} to list of trainings of this object,
	 * If does not exists Category-specific training with NANDO code, which is assigned
	 * in given object. 
	 * 
	 * @param category-specific training
	 * @return TRUE, If was successfully added. FALSE If exists any {@link CategorySpecificTraining} Object with NANDO code
	 * which is assigned in given {@link CategorySpecificTraining} object.
	 * 
	 * @see {@link NandoCode}
	 * @throws IllegalArgumentException if is given object NULL, or NANDO code is not set.
	 */
	@Transient
	public boolean addCategorySpecificTraining(final CategorySpecificTraining cst){
		Validate.notNull(cst);
		Validate.notNull(cst.getNandoCode());
		boolean found = false;
		for(CategorySpecificTraining perisitedCst : categorySpecificTrainings ){
			if(perisitedCst.getNandoCode().equals(cst.getNandoCode())){
				found = true;
				break;
			}
		}
		if(found){
			return false;
		}
		categorySpecificTrainings.add(cst);
		return true;
	}
	
	
	@Transient
	public boolean removeCategorySpecificTraining(final Long id){
		Iterator<CategorySpecificTraining> i = categorySpecificTrainings.iterator();
		while (i.hasNext()) {
		   final CategorySpecificTraining cst = i.next();
		   if(cst.getId().equals(id)){
			   i.remove();
			   return true;
		   }
		}
		return false;
	}
	
	@Transient
	public int getTotalHours(){
		int hours = 0;
		for(CategorySpecificTraining cst : categorySpecificTrainings){
			hours += cst.getHours();
		}
		hours += iso13485 + iso9001 +mdd + ivd + aimd + nb1023Procedures;
		return hours;
	}
	
	@Transient
	public boolean containsAuditor(final Long auditorId){
		for(final Auditor a: auditors){
			if(a.getId().equals(auditorId)){
				return true;
			}
		}
		return false;
	}
	
	@Transient
	public boolean getBaseDateAreSet(){
		return getTotalHours() > 0 && StringUtils.isNotBlank(getSubject()) && date != null; 
	}
	
	@Transient
	public boolean isAttachmentUploaded(){
		return StringUtils.isNotBlank(attachment);
	}
	
	@Transient 
	public boolean isAuditorListSet(){
		return auditors.size() > 0; 
	}
	
	@Transient 
	public boolean isValid(){
		return isAttachmentUploaded() && getBaseDateAreSet() && isAuditorListSet();
	}
	
	@Transient 
	public String getFormatedWorkers(){
		if(auditors.size() == 0){
			return "0";
		}
		Iterator<Auditor> it = auditors.iterator();
		final String firstAuditor = it.next().getName();
		if(auditors.size() == 1){
			return firstAuditor;
		}
		final String secondAuditor = it.next().getName();
		if(auditors.size() == 2){
			return firstAuditor + " and " + secondAuditor;
		}
		return firstAuditor +", "+ secondAuditor +" and " + (auditors.size() - 1) + " others ...";
	}
	
}
