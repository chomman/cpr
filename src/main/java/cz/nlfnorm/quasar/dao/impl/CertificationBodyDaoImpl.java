package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.CertificationBodyDao;
import cz.nlfnorm.quasar.entities.CertificationBody;
import cz.nlfnorm.quasar.entities.Company;

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

	@Override
	public CertificationBody findByName(final String name) {
		final String sql = "select * from quasar_certification_body where " + 
				 "where  regexp_replace(unaccent(lower(name)), '[^a-z]', '', 'g') = " + 
			     "regexp_replace(unaccent(lower(:name)), '[^a-zA-Z]', '', 'g')";
		return (CertificationBody)sessionFactory.getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(Company.class)
				.setString("name", name)
				.setReadOnly(true)
				.setMaxResults(1)
				.uniqueResult();
	}
	
	

}
