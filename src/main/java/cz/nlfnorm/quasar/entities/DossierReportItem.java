package cz.nlfnorm.quasar.entities;

import java.util.Set;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cz.nlfnorm.quasar.enums.CertificationSuffix;
import cz.nlfnorm.quasar.enums.DossierReportCategory;
import cz.nlfnorm.quasar.web.forms.CompanyForm;
import cz.nlfnorm.utils.NlfStringUtils;

@Entity
@SequenceGenerator(name = "quasar_log_item_id_seq", sequenceName = "quasar_log_item_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "quasar_dossier_report_has_item")
public class DossierReportItem extends AbstractLogItem 
	implements LogItem, CompanyForm{

	private static final long serialVersionUID = 2097701233043566320L;
	
	private DossierReport dossierReport;
	private String certificationNo;
	private String certificationSufix;
	private DossierReportCategory category;
	
	public DossierReportItem(){}
	
	public DossierReportItem(DossierReport report){
		dossierReport = report;
	}
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quasar_log_item_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@NotEmpty(message = "{error.dossierReportItem.certificationNo.required}")
	@Pattern(regexp = "^14\\d{4}$", message = "{error.dossierReportItem.certificationNo}")
	@Column(name = "certification_no", length = 6)	
	public String getCertificationNo() {
		return certificationNo;
	}

	public void setCertificationNo(String certificationNo) {
		this.certificationNo = certificationNo;
	}
	
	@Pattern(regexp = "^[a-zA-Z]{1,2}\\/(NB|nb)$", message = "{error.dossierReportItem.certificationSufix}")
	@Column(name = "certification_sufix", length = 5, nullable = false)	
	public String getCertificationSufix() {
		return certificationSufix;
	}

	public void setCertificationSufix(String certificationSufix) {
		this.certificationSufix = certificationSufix;
	}
	
	@Transient
	public String getCerfication(){
		return NlfStringUtils.insertCharatersAt(2, certificationNo, " ") + " " + certificationSufix;
	}

	@NotNull(message = "{error.dossierReportItem.category}")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "category", nullable = false, length = 6)
	public DossierReportCategory getCategory() {
		return category;
	}

	public void setCategory(DossierReportCategory category) {
		this.category = category;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "quasar_dossier_report_item_has_nando_code", joinColumns = @JoinColumn(name = "dossier_report_item_id"), inverseJoinColumns = @JoinColumn(name = "nando_code_id"))
	public Set<NandoCode> getNandoCodes() {
		return super.getNandoCodes();
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_report_id", nullable = false)
	public DossierReport getDossierReport() {
		return dossierReport;
	}

	public void setDossierReport(DossierReport dossierReport) {
		this.dossierReport = dossierReport;
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
	
	/**
	 * Return TRUE if this dossier report item is <b>Design Dossier</b> type. 
	 * 
	 * Design Dossier is if are satisfied following conds.:
	 * <ul>
	 * 	<li>Category III  + Cert suffix equals CN/NB</li>
	 *  <li>Category LIST A  + Cert suffix equals CN/NB</li>
	 * </ul
	 * 
	 * @return TRUE, if this item is Design Dossier, false otherwise
	 */
	@Transient
	public boolean isDesignDossier(){
		return certificationSufix != null && 
			   category != null &&
			   CertificationSuffix.CNNB.getName().equals(certificationSufix) &&
			  (
					  category.equals(DossierReportCategory.III) || 
					  category.equals(DossierReportCategory.LIST_A)
			  );
			
	}

}
