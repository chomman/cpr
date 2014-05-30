package cz.nlfnorm.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.TagDao;
import cz.nlfnorm.entities.Tag;

@Repository("tagDao")
public class TagDaoImpl extends BaseDaoImpl<Tag, Long> implements TagDao{

	public TagDaoImpl(){
		super(Tag.class);
	}
	
	
	@Override
	public Tag getByCode(String code) {
		throw new UnsupportedOperationException();
	}


	@Override
	public Tag getTagByName(final String name) {
		if(StringUtils.isBlank(name)){
			return null;
		}
		Query query = sessionFactory.getCurrentSession()
					  .createQuery("from Tag tag where unaccent(lower(tag.name)) = unaccent(lower(:name))");
		query.setMaxResults(1);
		query.setCacheable(false);
		query.setString("name", name);
		return (Tag)query.uniqueResult();
	}
	
	
}
