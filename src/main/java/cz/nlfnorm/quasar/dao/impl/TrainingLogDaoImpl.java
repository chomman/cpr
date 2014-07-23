package cz.nlfnorm.quasar.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.dao.TrainingLogDao;
import cz.nlfnorm.quasar.entities.Partner;
import cz.nlfnorm.quasar.entities.TrainingLog;

@Repository("trainingLogDao")
public class TrainingLogDaoImpl extends AbstractLogDaoImpl<TrainingLog> implements TrainingLogDao{

	public TrainingLogDaoImpl(){
		super(TrainingLog.class);
	}

	@Override
	protected String getAuditorJoinClouse() {
		return " al join al.auditors auditor left join auditor.partner partner ";
	}
	
	protected String prepareHqlForQuery(final Map<String, Object> criteria){
		String query = super.prepareHqlForQuery(criteria);
		
		return query;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void prepareAuditor(List<String> where, Map<String, Object> criteria) {
		final Long auditorId = (Long)criteria.get(AuditorFilter.AUDITOR);
		final List<Partner> parnters = (List<Partner>)criteria.get(AuditorFilter.PARTNERS); 
		if(auditorId != null && auditorId != 0l){
			String hql = "auditor.id=:"+ AuditorFilter.AUDITOR + 
						" or al.createdBy.id=:"+AuditorFilter.CREATED_BY;
			if(parnters != null){
				String ids = "";
				for(final Partner p : parnters){
					if(ids != ""){
						ids += ",";
					}
					ids += p.getId();
				}
				hql += " or partner.id in("+ids+")"; 
			}
			where.add("( " + hql +" )");
		}
	}
	
	@Override
	protected void prepareHqlQueryParams(Query query, Map<String, Object> criteria) {
		super.prepareHqlQueryParams(query, criteria);
		if(criteria.get(AuditorFilter.CREATED_BY) != null){
			query.setLong(AuditorFilter.CREATED_BY, (Long)criteria.get(AuditorFilter.CREATED_BY));
		}
	}
}
