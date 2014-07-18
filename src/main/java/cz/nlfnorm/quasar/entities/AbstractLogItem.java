package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

@MappedSuperclass
@SuppressWarnings("serial")
public class AbstractLogItem extends IdentifiableEntity{
	
	private Company company;
	private LocalDate auditDate;
	private String certifiedProduct;
	private String orderNo;
	private Set<NandoCode> nandoCodes = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@NotNull(message = "{error.auditDate}")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "audit_date", nullable = false)
	public LocalDate getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(LocalDate auditDate) {
		this.auditDate = auditDate;
	}
	
	@Length(min = 1, max = 255, message = "{error.certifiedProduct}")
	@Column(name= "cerfied_product", length = 255)
	public String getCertifiedProduct() {
		return certifiedProduct;
	}

	public void setCertifiedProduct(String certifiedProduct) {
		this.certifiedProduct = certifiedProduct;
	}
	
	@Pattern(regexp = "(^(8036|8136|8236)\\d{5}|)$", message = "{error.orderNo}")
	@Column(name = "order_no", length = 9)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Transient
	public Set<NandoCode> getNandoCodes() {
		return nandoCodes;
	}

	public void setNandoCodes(Set<NandoCode> nandoCodes) {
		this.nandoCodes = nandoCodes;
	}
	
	@Transient
	public void clearNandoCodes() {
		if(nandoCodes != null){
			nandoCodes.clear();
		}
	}
	@Transient
	public void addNandoCode(NandoCode nandoCode) {
		if(nandoCode != null){
			nandoCodes.add(nandoCode);
		}
	}
}
