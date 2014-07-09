package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.CertificationBodyDao;
import cz.nlfnorm.quasar.entities.CertificationBody;

@Repository("certificationBodyDao")
public class CertificationBodyDaoImpl extends BaseDaoImpl<CertificationBody, Long> implements CertificationBodyDao{

	public CertificationBodyDaoImpl(){
		super(CertificationBody.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CertificationBody> autocomplete(final String term) {
		final String hql = "select cb from CertificationBody cb where " +
						   "unaccent(lower(cb.name)) like CONCAT(lower(unaccent(:term)),'%') ";
		return createQuery(hql)
					  .setString("term", term.toLowerCase().trim())
					  .setMaxResults(8)
					  .setCacheable(false)
					  .list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CertificationBody> getAll() {
		return createQuery("select cb from CertificationBody cb order by cb.name")
				.list();
	}
	
	

}
