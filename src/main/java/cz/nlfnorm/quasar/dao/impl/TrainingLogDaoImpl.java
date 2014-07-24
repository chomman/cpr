package cz.nlfnorm.quasar.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.constants.AuditorFilter;
import cz.nlfnorm.quasar.dao.TrainingLogDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.entities.TrainingLog;

@Repository("trainingLogDao")
public class TrainingLogDaoImpl extends AbstractLogDaoImpl<TrainingLog> implements TrainingLogDao{

	public TrainingLogDaoImpl(){
		super(TrainingLog.class);
	}

	@Override
	protected String getAuditorJoinClouse() {
		return " al left join al.auditors auditor left join auditor.partner partner ";
	}
	
	protected String prepareHqlForQuery(final Map<String, Object> criteria){
		String query = super.prepareHqlForQuery(criteria);
		
		return query;
	}
	

	@Override
	protected void prepareAuditor(List<String> where, Map<String, Object> criteria) {
		final Long auditorId = (Long)criteria.get(AuditorFilter.AUDITOR);
		if(auditorId != null && auditorId != 0l){
			String hql = "auditor.id=:"+ AuditorFilter.AUDITOR + 
						" or al.createdBy.id=:"+AuditorFilter.CREATED_BY +
						" or partner.id IN (SELECT pt.id FROM Partner pt WHERE pt.manager.id =:"+AuditorFilter.CREATED_BY+")"; 
			where.add(" ( " + hql +" ) ");
		}
	}
	
	@Override
	protected void prepareHqlQueryParams(Query query, Map<String, Object> criteria) {
		super.prepareHqlQueryParams(query, criteria);
		if(criteria.get(AuditorFilter.CREATED_BY) != null){
			query.setLong(AuditorFilter.CREATED_BY, (Long)criteria.get(AuditorFilter.CREATED_BY));
		}
	}
	
	@Override
	protected String getAuditorGroupByClouse() {
		return " GROUP BY al.id ";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Auditor> getAllUnassignedAuditorsToLog(final TrainingLog log){
		final String hql = 
				"select a from Auditor a WHERE a.id NOT IN " +
				"(select auditor.id from TrainingLog log join log.auditors auditor where log.id=:id)";			
		return createQuery(hql)
			.setLong("id", log.getId())
			.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Auditor> getAllUnassignedAuditorsToLog(final long logId, final long userId){
		final String sql = 
				"SELECT a.*,u2.* FROM quasar_auditor a "+
				"JOIN quasar_partner partner ON partner.id = a.partner_id "+
				"INNER JOIN users u2 ON u2.id = a.id "+
				"WHERE partner.id IN (SELECT p.id FROM quasar_partner p WHERE p.id_manager=:userId ) "+
				"AND a.id NOT IN (SELECT lha.auditor_id FROM quasar_training_log_has_auditors lha WHERE lha.training_log_id = :logId)";
		return getSessionFactory().getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(Auditor.class)
				.setLong("userId", userId)
				.setLong("logId", logId)
				.list();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<NandoCode> getAllUnassignedNandoCodesForLog(final TrainingLog log){
		final String hql = 
					"SELECT code FROM NandoCode code " +
					"WHERE (code.forProductAssesorA=true OR code.forProductAssesorR=true OR code.forProductSpecialist=true) " +
					"AND code.id NOT IN (SELECT cst.nandoCode.id FROM CategorySpecificTraining cst WHERE cst.trainingLog.id=:id)"; 
		return createQuery(hql)
				.setLong("id", log.getId())
				.setReadOnly(true)
				.list();
	}
}
