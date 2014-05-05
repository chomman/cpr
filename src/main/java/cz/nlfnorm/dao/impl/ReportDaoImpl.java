package cz.nlfnorm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.ReportDao;
import cz.nlfnorm.entities.Report;

/**
 * Entita, reprezentujuta mesacny report zmien noriem
 * @author peto
 */
@Repository("reportDao")
public class ReportDaoImpl extends BaseDaoImpl<Report, Long> implements ReportDao {
	
	public ReportDaoImpl() {
		super(Report.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Report> getReports(final boolean enabledOnly) {
		StringBuilder hql = new StringBuilder("FROM ");
		hql.append(Report.class.getName());
		hql.append(" r");
		if(enabledOnly){
			hql.append(" WHERE enabled = true  ");
		}
		hql.append( " ORDER BY r.dateFrom desc");
		return sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.list();
	}
	
}
