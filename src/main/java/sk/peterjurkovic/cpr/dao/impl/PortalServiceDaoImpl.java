package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.PortalServiceDao;
import sk.peterjurkovic.cpr.entities.PortalService;

@Repository("portalServiceDao")
public class PortalServiceDaoImpl extends BaseDaoImpl<PortalService, Long> implements PortalServiceDao {

	public PortalServiceDaoImpl() {
		super(PortalService.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<PortalService> getAllNotDeleted(final boolean publishedOnly) {
		StringBuilder hql = new StringBuilder("FROM ");
		hql.append(PortalService.class.getName());
		hql.append(" s where s.deleted = false ");
		if(publishedOnly){
			hql.append(" and s.enabled = true  ");
		}
		hql.append( " ORDER BY s.created desc");
		return sessionFactory.getCurrentSession()
				.createQuery(hql.toString())
				.list();
	}

}
