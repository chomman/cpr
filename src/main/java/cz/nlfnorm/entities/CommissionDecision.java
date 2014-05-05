package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import cz.nlfnorm.utils.RequestUtils;

/**
 * Entita reprezentujuca rozhodnutie komisie
 * 
 * @author Peter Jurkovič
 * @date Dec 8, 2013
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "commission_decision_id_seq", sequenceName = "commission_decision_id_seq", initialValue = 1, allocationSize =1)
@Table(name = "commission_decision")
public class CommissionDecision extends AbstractEntity {
	

	private static final long serialVersionUID = 2165854576870628957L;
	
	/*
	 * Cesky nnchor text rozhodnutia
	 */
	private String czechLabel;
	/*
	 * URL na rozhodnutie komisie v cestine 
	 */
	private String czechFileUrl;
	/*
	 * Anglicky nnchor text rozhodnutia
	 */
	private String englishLabel;
	/*
	 * URL na rozhodnutie komisie v anglictine
	 */
	private String englishFileUrl;
	
	/*
	 * URL na navrh zmeny
	 */
	private String draftAmendmentLabel;
	private String draftAmendmentUrl;
	
	/*
	 * Jedna sa o konsolidovane zneni 
	 */
	private Boolean consolidatedVersion = Boolean.FALSE;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commission_decision_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@NotBlank(message = "České označení musí být vyplněno" )
	@Length(max = 20)
	@Column(name = "czech_label", length = 20)
	public String getCzechLabel() {
		return czechLabel;
	}

	public void setCzechLabel(String czechLabel) {
		this.czechLabel = czechLabel;
	}
	@Length(max = 150)
	@Column(name = "czech_file_url", length = 150)
	public String getCzechFileUrl() {
		return czechFileUrl;
	}

	public void setCzechFileUrl(String czechFileUrl) {
		this.czechFileUrl = czechFileUrl;
	}
	
	@Length(max = 20)
	@Column(name = "english_label", length = 20)
	public String getEnglishLabel() {
		return englishLabel;
	}

	public void setEnglishLabel(String englishLabel) {
		this.englishLabel = englishLabel;
	}
	
	@Length(max = 150)
	@Column(name = "english_file_url", length = 150)
	public String getEnglishFileUrl() {
		return englishFileUrl;
	}

	public void setEnglishFileUrl(String englishFileUrl) {
		this.englishFileUrl = englishFileUrl;
	}
	@Length(max = 150)
	@Column(name = "draft_amendment_file_url", length = 150)
	public String getDraftAmendmentUrl() {
		return draftAmendmentUrl;
	}

	public void setDraftAmendmentUrl(String draftAmendmentUrl) {
		this.draftAmendmentUrl = draftAmendmentUrl;
	}

	public String getDraftAmendmentLabel() {
		return draftAmendmentLabel;
	}
	
	@Length(max = 25)
	@Column(name = "draft_amendment_label", length = 25)
	public void setDraftAmendmentLabel(String draftAmendmentLabel) {
		this.draftAmendmentLabel = draftAmendmentLabel;
	}
	
	@Column(name = "consolidated_version")
	public Boolean getConsolidatedVersion() {
		return consolidatedVersion;
	}


	public void setConsolidatedVersion(Boolean consolidatedVersion) {
		this.consolidatedVersion = consolidatedVersion;
	}
	

	@Override
	public String toString() {
		return "CommissionDecision [czechLabel=" + czechLabel
				+ ", czechFileUrl=" + czechFileUrl + ", englishLabel="
				+ englishLabel + ", englishFileUrl=" + englishFileUrl
				+ ", draftAmendmentLabel=" + draftAmendmentLabel
				+ ", draftAmendmentUrl=" + draftAmendmentUrl + "]";
	}

	
	@Transient
	public String getLabel(){
		if(!RequestUtils.isCzechLocale() && StringUtils.isNotBlank(englishLabel)){
			return englishLabel;
		}
		return czechLabel;
	}
	
	@Transient
	public String getUrl(){
		if(!RequestUtils.isCzechLocale() && StringUtils.isNotBlank(englishFileUrl)){
			return englishFileUrl;
		}
		return czechFileUrl;
	}
	
}
