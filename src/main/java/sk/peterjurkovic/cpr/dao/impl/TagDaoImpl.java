package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.TagDao;
import sk.peterjurkovic.cpr.entities.Tag;

/**
 * Implementacia rozhrania pre pracu so stitkami {@link sk.peterjurkovic.cpr.dao.TagDao}
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 */
@Repository("tagDao")
public class TagDaoImpl extends BaseDaoImpl<Tag, Long> implements TagDao{
	
	public TagDaoImpl(){
		super(Tag.class);
	}
	
	
	/**
	 * Vyhlada vsetky stitky, podla daneho nazvu
	 * 
	 * @param term - vyraz, na zaklade ktoreho sa vykona hladanie
	 * @return List<Tag> zoznam vyhovujucich tago
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> searchByTagName(String tagName) {
		StringBuilder hql = new StringBuilder("select tag.id, tag.name from Tag tag");
		hql.append(" JOIN tag.standard as standard");
		hql.append(" where tag.name like CONCAT('%', :tagName , '%')");
		hql.append(" and standard.enabled=true and size(standard.requirements) > 0");

		Query hqlQuery =  sessionFactory.getCurrentSession().createQuery(hql.toString());
		hqlQuery.setParameter("tagName", tagName);
		hqlQuery.setMaxResults(6);
		return hqlQuery.list();
	}
}
