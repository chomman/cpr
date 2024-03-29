package cz.nlfnorm.quasar.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.CompanyDao;
import cz.nlfnorm.quasar.entities.Company;

@Repository("companyDao")
public class CompanyDaoImpl extends BaseDaoImpl<Company, Long> implements CompanyDao{

	public CompanyDaoImpl(){
		super(Company.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> autocomplete(final String term) {
		final String hql = "select c from Company c where " +
						   "unaccent(lower(c.name)) like CONCAT(lower(unaccent(:term)),'%') ";
		return createQuery(hql)
					  .setString("term", term.toLowerCase().trim())
					  .setMaxResults(8)
					  .setCacheable(false)
					  .list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getAll() {
		return createQuery("select c from Company c order by c.name")
				.list();
	}


	@Override
	public Company findByName(String name) {
		final String sql = "select * from quasar_company where " + 
					 "regexp_replace(unaccent(lower(name)), '[^a-z]', '', 'g') = " + 
				     "regexp_replace(unaccent(lower(:name)), '[^a-zA-Z]', '', 'g')";
		return (Company)sessionFactory.getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(Company.class)
				.setString("name", name)
				.setReadOnly(true)
				.setMaxResults(1)
				.uniqueResult();

	}
	
}
