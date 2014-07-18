package cz.nlfnorm.quasar.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import cz.nlfnorm.quasar.enums.DossierReportCategory;
import cz.nlfnorm.quasar.web.forms.CompanyForm;

@Entity
@SequenceGenerator(name = "quasar_log_item_id_seq", sequenceName = "quasar_log_item_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_dossier_report_has_item")
public class DossierReportItem extends AbstractLogItem 
	implements LogItem, CompanyForm{

	private static final long serialVersionUID = 2097701233043566320L;
	
	private String certificationNo;
	private String certificationSufix;
	private DossierReportCategory category;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_log_item_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Pattern(regexp = "(\\d{6}$", message = "{error.dossierReportItem.certificationNo}")
	@Column(name = "certification_no", length = 6)	
	public String getCertificationNo() {
		return certificationNo;
	}

	public void setCertificationNo(String certificationNo) {
		this.certificationNo = certificationNo;
	}
	
	@Pattern(regexp = "^[a-z]{1,2}\\/(NB|nb)$", message = "{error.dossierReportItem.certificationSufix}")
	@Column(name = "certification_sufix", length = 5)	
	public String getCertificationSufix() {
		return certificationSufix;
	}

	public void setCertificationSufix(String certificationSufix) {
		this.certificationSufix = certificationSufix;
	}

	@Type(type="cz.nlfnorm.quasar.hibernate.LogStatusUserType")
	@Column(name = "category_id")
	public DossierReportCategory getCategory() {
		return category;
	}

	public void setCategory(DossierReportCategory category) {
		this.category = category;
	}

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "quasar_dossier_report_item_has_nando_code", joinColumns = @JoinColumn(name = "dossier_report_item_id"), inverseJoinColumns = @JoinColumn(name = "nando_code_id"))
	public Set<NandoCode> getNandoCodes() {
		return super.getNandoCodes();
	}

	@Transient
	@Override
	public void clearEacCodes() {
		throw new UnsupportedOperationException();
	}
	@Transient
	@Override
	public void addEacCode(EacCode eacCode) {
		throw new UnsupportedOperationException();
	}
}
