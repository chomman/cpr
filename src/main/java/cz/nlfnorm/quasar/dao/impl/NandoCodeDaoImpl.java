package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.NandoCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;

/**
 * Hibernate implementation of interface NandoCodeDao
 * 
 * @author Peter Jurkovic
 * @date Jun 11, 2014
 */
@Repository("nandoCodeDao")
public class NandoCodeDaoImpl extends BaseDaoImpl<NandoCode, Long> implements NandoCodeDao{
	
	private final static int ALL_AUDITORS = -1;
	
	public NandoCodeDaoImpl(){
		super(NandoCode.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<NandoCode> getCodesForAuditorType(final int type, final boolean onlyFirstLevel) {
		StringBuilder hql = new StringBuilder("select n from NandoCode n where n.enabled=true ");
		switch(type){
			case Auditor.TYPE_PRODUCT_ASSESSOR_A :
				hql.append(" n.forProductAssesorA = true ");
			break;
			case Auditor.TYPE_PRODUCT_ASSESSOR_R :
				hql.append(" n.forProductAssesorR = true ");
			break;
			case Auditor.TYPE_PRODUCT_SPECIALIST :
				hql.append(" n.forProductSpecialist = true ");
			break;
		}
		if(onlyFirstLevel){
			hql.append(" and n.parent IS NOT NULL ");
		}
		hql.append(" order by coalesce (n.parent.order, n.order) ");
		return createQuery(hql).setCacheable(false).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<NandoCode> getFirstLevelCodes() {
		StringBuilder hql = new StringBuilder("select n from NandoCode n ");
		hql.append(" where n.parent is NULL order by n.order ASC ");
		Query query = createQuery(hql);
		query.setCacheable(false);
		return query.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<NandoCode> getSecondLevelCodes() {
		StringBuilder hql = new StringBuilder("select n from NandoCode n ");
		hql.append(" where n.parent is not NULL order by n.parent.order, n.order DESC ");
		Query query = createQuery(hql);
		query.setCacheable(false);
		return query.list();
	}


	@Override
	public NandoCode getByNandoCode(final String code) {
		StringBuilder hql = new StringBuilder("from NandoCode n ");
		hql.append(" where n.code = :code ");
		Query query = createQuery(hql);
		query.setString("code", code.toLowerCase());
		query.setMaxResults(1);
		query.setCacheable(false);
		return (NandoCode) query.uniqueResult();
	}
	
	@Override
	public int getNextOrderInNode(Long nodeId) {
		StringBuilder hql = new StringBuilder("select max(n.order) from NandoCode n ");
		if(nodeId == null){
			hql.append(" where n.parent.id = null ");
		}else{
			hql.append(" where n.parent.id = :id");
		}
		
		Query hqlQuery =  createQuery(hql);
		if(nodeId != null){
			hqlQuery.setLong("id", nodeId );
		}
		
		Integer maxOrder = (Integer) hqlQuery.setMaxResults(1).uniqueResult();
		
		if(maxOrder != null){
			return maxOrder + 1;
		}
		return 1;
	}

	@Override
	public List<NandoCode> getAllEnabled() {
		return getCodesForAuditorType(ALL_AUDITORS, false);
	}
}
