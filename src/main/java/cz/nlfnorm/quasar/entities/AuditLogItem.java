package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

import cz.nlfnorm.quasar.enums.AuditLogItemType;

@Entity
@SequenceGenerator(name = "quasar_log_item_id_seq", sequenceName = "quasar_log_item_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_audit_log_has_item")
public class AuditLogItem extends IdentifiableEntity{

	private static final long serialVersionUID = 8711651499246430978L;
	
	private AuditLog auditLog;
	private Company company;
	private LocalDate auditDate;
	private int durationInDays = 1;
	private String certifiedProduct;
	private CertificationBody certificationBody;
	private String orderNo;
	private AuditLogItemType type;
	
	private Set<NandoCode> nandoCodes = new HashSet<>();
	private Set<EacCode> eacCodes  = new HashSet<>();
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_log_item_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_log_id", nullable = false)
	public AuditLog getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Min(value = 1, message = "{error.auditLogItem.durationInDays}")
	@Column(name = "days", nullable = false)
	public int getDurationInDays() {
		return durationInDays;
	}
	
	public void setDurationInDays(int durationInDays) {
		this.durationInDays = durationInDays;
	}
	
	@NotNull(message = "{error.auditLogItem.auditDate}")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "audit_date", nullable = false)
	public LocalDate getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(LocalDate auditDate) {
		this.auditDate = auditDate;
	}
	
	@Length(min = 1, max = 255, message = "{error.auditLogItem.certifiedProduct}")
	@Column(name= "cerfied_product", length = 255)
	public String getCertifiedProduct() {
		return certifiedProduct;
	}

	public void setCertifiedProduct(String certifiedProduct) {
		this.certifiedProduct = certifiedProduct;
	}
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "certification_body_id")	
	public CertificationBody getCertificationBody() {
		return certificationBody;
	}

	public void setCertificationBody(CertificationBody certificationBody) {
		this.certificationBody = certificationBody;
	}

	@Pattern(regexp = "(^(8036|8136|8236)\\d{5}|)$")
	@Column(name = "order_no", length = 9)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "item_type", length = 8, nullable = false)
	public AuditLogItemType getType() {
		return type;
	}

	public void setType(AuditLogItemType type) {
		this.type = type;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "quasar_audit_log_item_has_nando_code", joinColumns = @JoinColumn(name = "audit_log_item_id"), inverseJoinColumns = @JoinColumn(name = "nando_code_id"))
	public Set<NandoCode> getNandoCodes() {
		return nandoCodes;
	}

	public void setNandoCodes(Set<NandoCode> nandoCodes) {
		this.nandoCodes = nandoCodes;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "quasar_audit_log_item_has_eac_code", joinColumns = @JoinColumn(name = "audit_log_item_id"), inverseJoinColumns = @JoinColumn(name = "eac_code_id"))
	public Set<EacCode> getEacCodes() {
		return eacCodes;
	}

	public void setEacCodes(Set<EacCode> eacCodes) {
		this.eacCodes = eacCodes;
	}

}
