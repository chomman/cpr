package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import sk.peterjurkovic.cpr.enums.CsnTerminologyLanguage;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "csn_terminology")
public class CsnTerminology extends AbstractEntity {

	private static final long serialVersionUID = -8939004150752259738L;
	
	private Long id;
	
	private Csn csn;
	
	private CsnTerminologyLanguage language;
	
	private String title;
	
	private String content;

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csn_id")
	public Csn getCsn() {
		return csn;
	}

	public void setCsn(Csn csn) {
		this.csn = csn;
	}
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "lang", length = 2)
	public CsnTerminologyLanguage getLanguage() {
		return language;
	}

	public void setLanguage(CsnTerminologyLanguage language) {
		this.language = language;
	}

	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column
	@Type(type = "text")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
