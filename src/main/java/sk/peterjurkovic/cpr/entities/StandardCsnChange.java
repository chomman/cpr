package sk.peterjurkovic.cpr.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.enums.StandardStatus;


@Entity
@Table(name = "standard_csn_has_change")
@SequenceGenerator(name = "standard_csn_change_id_seq", sequenceName = "standard_csn_change_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
public class StandardCsnChange extends AbstractStandardCsn {

	private static final long serialVersionUID = 5153211394849046656L;

	private StandardCsn standardCsn;
	
	public StandardCsnChange(){
		setCreated(new LocalDateTime());
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_csn_change_id_seq")
	public Long getId() {
		return super.getId();
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
	public StandardCsn getStandardCsn() {
		return standardCsn;
	}


	public void setStandardCsn(StandardCsn standardCsn) {
		this.standardCsn = standardCsn;
	}
	
	@Override
	@Transient
	public StandardStatus getStandardStatus() {	
		return super.getStandardStatus();
	}
	
}
