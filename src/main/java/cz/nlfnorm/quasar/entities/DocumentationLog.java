package cz.nlfnorm.quasar.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * QUASAR entity. Represent Auditor documentation log (TF and DD reviews)
 * 
 * @author Peter Jurkovic
 * @date Jul 16, 2014
 */
@Entity
@Table(name = "quasar_documentation_log")
public class DocumentationLog extends AbstractLog {

	private static final long serialVersionUID = -5150040899742422009L;

	public DocumentationLog(){}
	
	public DocumentationLog(Auditor auditor){
		super(auditor);
	}
	
	@Override
	public String toString() {
		return "DocumentationLog [getId()=" + getId() + "]";
	}
	
}
