package cz.nlfnorm.dao.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.PortalProductDao;
import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.enums.PortalProductType;

@Repository("portalProductDao")
public class PortalProductDaoImpl extends BaseDaoImpl<PortalProduct, Long> implements PortalProductDao {

	public PortalProductDaoImpl() {
		super(PortalProduct.class);
	}

	
	@Override
	public List<PortalProduct> getAllNotDeleted(final boolean publishedOnly) {
		return getAllByType( null , publishedOnly);
	}


	
	@Override
	public List<PortalProduct> getAllOnlinePublications(final boolean publishedOnly) {
		return getAllByType(PortalProductType.PUBLICATION, publishedOnly);
	}
	
	
	
	@Override
	public List<PortalProduct> getAllRegistrations(final boolean publishedOnly) {
		return getAllByType(PortalProductType.REGISTRATION, publishedOnly);
	}
	
	
	@SuppressWarnings("unchecked")
	private List<PortalProduct> getAllByType(final PortalProductType type, final boolean publishedOnly){
		StringBuilder hql = new StringBuilder("FROM ");
		hql.append(PortalProduct.class.getName());
		hql.append(" s where s.deleted = false ");
		
		if(type != null){
			hql.append(" and s.portalProductType = :type");
		}
		
		if(publishedOnly){
			hql.append(" and s.enabled = true  ");
		}
		hql.append( " ORDER BY s.created desc");
		Query query = sessionFactory.getCurrentSession()
				.createQuery(hql.toString());
		if(type != null){
			query.setParameter("type", type);
		}				
		return query.list();
	}


	@Override
	public PortalProduct getByOnlinePublication(final String publicationCode) {
		Validate.notEmpty(publicationCode);
		StringBuilder hql = new StringBuilder("FROM ");
		hql.append(PortalProduct.class.getName());
		hql.append(" s where s.onlinePublication = :publicationCode ");
		Query query = sessionFactory.getCurrentSession()
				.createQuery(hql.toString());
		query.setString("publicationCode", publicationCode);
		query.setMaxResults(1);
		query.setCacheable(false);
		return (PortalProduct)query.uniqueResult();
	}

}
