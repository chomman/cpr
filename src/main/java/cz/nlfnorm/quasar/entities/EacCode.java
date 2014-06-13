package cz.nlfnorm.quasar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * Entity of Quality system assesmet reporting system, represents EAC code
 * 
 * @author Peter Jurkovic
 * @date Jun 6, 2014
 */
@Entity
@SequenceGenerator(name = "quasar_eac_code_id_seq", sequenceName = "quasar_eac_code_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "quasar_eac_code")
public class EacCode extends BaseEntity {

	private static final long serialVersionUID = 1385780507346969333L;
	
	private String name;
	private String naceCode;
	
	private boolean forQsAuditor;
	private Integer threshold = 1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_eac_code_id_seq")
	@Override
	public Long getId() {
		return super.getId();
	}
	
	/**
	 * Mark of EAC code
	 * @return mark
	 */
	@Override
	@Pattern(regexp = "(?i)eac\\s\\d{1,2}", message = "{error.eacCode.code.invalid}")
	@Column(length = 6, name = "code")
	public String getCode() {
		return super.getCode();
	}
	
	/**
	 * Description of this EAC code
	 * @return description
	 */
	@Length(message = "{error.eacCode.name}", max = 120)
	@Column(name = "name", length = 120)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns information about, if is this EAC code in QS Auditor scope
	 * @return TRUE if is this EAC code in QS Auditors scope
	 */
	@Column(name = "is_for_qs_auditor", length = 120)
	public boolean isForQsAuditor() {
		return forQsAuditor;
	}

	public void setForQsAuditor(boolean forQsAuditor) {
		this.forQsAuditor = forQsAuditor;
	}
	
	/**
	 * Standard Industrial Classification (SIC) codes are four digit 
	 * numerical codes assigned by the U.S. government to business 
	 * establishments to identify the primary business of the establishment. 
	 * 
	 * @return Appropriate NACE code for this EAC code
	 */
	@Length(max = 100)
	@Column(name = "nace_code", length = 100)
	public String getNaceCode() {
		return naceCode;
	}	
	public void setNaceCode(String naceCode) {
		this.naceCode = naceCode;
	}
	
	/**
	 * Condition for total number of audits.
	 * 
	 * if (Number of NB 1023 audits in TA) + (Number of ISO 13485 audits in TA)
	 * in given auditors EAC code, than is condition evaluated as TRUE for 
	 * given EAC code.
	 *  
	 * @return threashold of this EAC code audits to fulfill the conditions
	 * @see {@link AuditorEacCode} 
	 */
	@Min(value = 0)
	@NotNull(message = "{error.eacCode.threshold}")
	@Column(name = "audit_threashold", nullable = false)
	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
		
	
}
