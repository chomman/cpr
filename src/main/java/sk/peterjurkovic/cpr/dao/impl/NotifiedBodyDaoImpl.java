package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.NotifiedBodyDao;
import sk.peterjurkovic.cpr.entities.NotifiedBody;


@Repository("notifiedBody")
public class NotifiedBodyDaoImpl extends BaseDaoImpl<NotifiedBody, Long> implements NotifiedBodyDao {
	
	public NotifiedBodyDaoImpl(){
		super(NotifiedBody.class);
	}

	
	@Override
	public boolean canBeDeleted(final NotifiedBody notifiedBody) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s");
		hql.append(" WHERE :notifiedBody IN ELEMENTS(s.notifiedBodies)");
		Long result = (Long)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setEntity("notifiedBody", notifiedBody)
						.uniqueResult();
		return (result == 0);
	}


	@Override
	public boolean isNameUniqe(final String code,final Long id) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM NotifiedBody nb");
		hql.append(" WHERE nb.code=:code AND nb.id<>:id");
		Long result = (Long)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setString("code", code)
						.setLong("id", id)
						.uniqueResult();
		return (result == 0);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<NotifiedBody> getNotifiedBodiesGroupedByCountry() {
		StringBuilder hql = new StringBuilder("FROM NotifiedBody nb ");
		hql.append("ORDER BY nb.country.id");
		return (List<NotifiedBody>)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.list();
	}

}
