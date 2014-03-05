package sk.peterjurkovic.cpr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.drew.lang.annotations.NotNull;


@Entity
@Table(name="report")
@SequenceGenerator(name = "report_id_seq", sequenceName = "report_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Report extends AbstractEntity {
		
	private static final long serialVersionUID = 1971363773832377709L;
	
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String contentCzech;
	private String contentEnglish;
	
	public Report() {
		LocalDate date = new LocalDate();
		setDateFrom(date.withDayOfMonth(1));
		setDateTo(date.dayOfMonth().withMaximumValue());
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@NotNull
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "date_from")
	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	@NotNull
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "date_to")
	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	
	@Type(type = "text")
	@Column(name = "content_czech")
	public String getContentCzech() {
		return contentCzech;
	}

	public void setContentCzech(String contentCzech) {
		this.contentCzech = contentCzech;
	}
	
	@Type(type = "text")
	@Column(name = "content_english")
	public String getContentEnglish() {
		return contentEnglish;
	}

	public void setContentEnglish(String contentEnglish) {
		this.contentEnglish = contentEnglish;
	}
	
	
}
