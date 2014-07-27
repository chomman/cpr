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
 * QUASAR entity. Represent Auditor audit log
 * 
 * @author Peter Jurkovic
 * @date Jul 7, 2014
 */
@Entity
@Table(name = "quasar_audit_log")
public class AuditLog extends AbstractLog {
	
	private static final long serialVersionUID = -5586903173779695020L;
	private Set<AuditLogItem> items = new HashSet<>();
	private Auditor auditor;
	
	public AuditLog(){}
	
	public AuditLog(Auditor auditor){
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
	@OneToMany(mappedBy = "auditLog", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<AuditLogItem> getItems() {
		return items;
	}

	public void setItems(Set<AuditLogItem> items) {
		this.items = items;
	}
	
	@Transient
	public int getCountOfAudits(){
		return items.size();
	}
	
	@Transient
	public int getSumOfAuditDays(){
		int days = 0;
		for(final AuditLogItem item : items){
			days += item.getDurationInDays();
		}
		return days;
	}
	
	@Override
	public String toString() {
		return "AuditLog [id=" + getId() + "]";
	}
	
}
