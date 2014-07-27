package cz.nlfnorm.quasar.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

/**
 * QUASAR entity. Represent Auditor documentation log (TF and DD reviews)
 * 
 * @author Peter Jurkovic
 * @date Jul 16, 2014
 */
@Entity
@Table(name = "quasar_dossier_report")
public class DossierReport extends AbstractLog {

	private static final long serialVersionUID = -5150040899742422009L;
	private Set<DossierReportItem> items = new HashSet<>(); 
	private Auditor auditor;

	public DossierReport(){}
	
	public DossierReport(Auditor auditor){
		super();
		this.auditor = auditor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor_id")
	public Auditor getAuditor() {
		return auditor;
	}
	
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	
	@OrderBy(clause = "audit_date")
	@OneToMany(mappedBy = "dossierReport", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<DossierReportItem> getItems() {
		return items;
	}

	public void setItems(Set<DossierReportItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "DossierReport [getId()=" + getId() + "]";
	}
		
	@Transient
	public int getCoundOfDesignDossiers(){
		int count = 0;
		for(final DossierReportItem item : items){
			if(item.isDesignDossier()){
				count++;
			}
		}
		return count;
	}
	
	@Transient
	public int getCoundOfTechnicalFiles(){
		return items.size() - getCoundOfDesignDossiers();
	}
	
}
