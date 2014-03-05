package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.ReportDao;
import sk.peterjurkovic.cpr.entities.Report;

/**
 * Entita, reprezentujuta mesacny report zmien noriem
 * @author peto
 */
@Repository("reportDao")
public class ReportDaoImpl extends BaseDaoImpl<Report, Long> implements ReportDao {
	
	public ReportDaoImpl() {
		super(Report.class);
	}
	
}
