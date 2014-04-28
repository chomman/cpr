package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.PortalProductDao;
import sk.peterjurkovic.cpr.entities.PortalProduct;

@Repository("portalProductDao")
public class PortalProductDaoImpl extends BaseDaoImpl<PortalProduct, Long> implements PortalProductDao {

	public PortalProductDaoImpl() {
		super(PortalProduct.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<PortalProduct> getAllNotDeleted(final boolean publishedOnly) {
		StringBuilder hql = new StringBuilder("FROM ");
		hql.append(PortalProduct.class.getName());
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
