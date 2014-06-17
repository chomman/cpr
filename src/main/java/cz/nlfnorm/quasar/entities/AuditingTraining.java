package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *  Entity of Quality system assesmet reporting system, represents audits and training.
 * 
 * @author Peter Jurkovic
 * @date Jun 9, 2014
 */
@Entity
@GenericGenerator(name = "users_id_seq", strategy = "foreign", parameters = {@Parameter(name= "property", value = "auditor")})
@Table(name = "quasar_auditor_has_auditing_and_training")
public class AuditingTraining extends IdentifiableEntity{
	
	private static final long serialVersionUID = 2164228613780986562L;
	
	private Auditor auditor;
	
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
	
	private Set<SpecialTraining> specialTrainings;
	
	AuditingTraining(){}
	
	AuditingTraining(Auditor auditor){
		this.auditor = auditor;
		this.specialTrainings = new HashSet<>();
	}
	
	@Id
	@GeneratedValue(generator = "users_id_seq")
	public Long getId() {
		return super.getId();
	}


	@PrimaryKeyJoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
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
	
	@Column(name = "iso9001_hours")
	public int getIso9001() {
		return iso9001;
	}
	
	public void setIso9001(int iso9001) {
		this.iso9001 = iso9001;
	}
	
	@Column(name = "iso13485_hours")
	public int getIso13485() {
		return iso13485;
	}

	public void setIso13485(int iso13485) {
		this.iso13485 = iso13485;
	}
	
	@Column(name = "mdd_hours")
	public int getMdd() {
		return mdd;
	}

	public void setMdd(int mdd) {
		this.mdd = mdd;
	}

	@Column(name = "ivd_hours")
	public int getIvd() {
		return ivd;
	}

	public void setIvd(int ivd) {
		this.ivd = ivd;
	}
	
	@Column(name = "nb1023_procedures_hours")
	public int getNb1023Procedures() {
		return nb1023Procedures;
	}

	public void setNb1023Procedures(int nb1023Procedures) {
		this.nb1023Procedures = nb1023Procedures;
	}
	
	@Column(name = "totoal_of_audits")
	public int getTotalAudits() {
		return totalAudits;
	}

	public void setTotalAudits(int totalAudits) {
		this.totalAudits = totalAudits;
	}
	
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

	@OneToMany(mappedBy = "auditingTraining", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<SpecialTraining> getSpecialTrainings() {
		return specialTrainings;
	}

	public void setSpecialTrainings(Set<SpecialTraining> specialTrainings) {
		this.specialTrainings = specialTrainings;
	}
}

