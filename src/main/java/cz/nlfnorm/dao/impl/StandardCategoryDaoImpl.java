package cz.nlfnorm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.StandardCategoryDao;
import cz.nlfnorm.entities.Regulation;
import cz.nlfnorm.entities.StandardCategory;

@Repository("standardCategoryDao")
public class StandardCategoryDaoImpl extends BaseDaoImpl<StandardCategory, Long> implements StandardCategoryDao{
	
	public StandardCategoryDaoImpl(){
		super(StandardCategory.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Regulation> getAllUnassignedRegulationFor(final StandardCategory standardCategory) {
		final String hql = 
				"SELECT r FROM Regulation r " +
				"WHERE r.id NOT IN ("
				+ "	SELECT regulation.id "
				+ " FROM StandardCategory c "
				+ " JOIN c.regulations regulation "
				+ " WHERE c.id=:id"
				+ ") ORDER BY r.code"; 
			return createQuery(hql)
					.setLong("id", standardCategory.getId())
					.setReadOnly(true)
					.list();
	}

}
