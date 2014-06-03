package cz.nlfnorm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.NotifiedBodyDao;
import cz.nlfnorm.entities.NotifiedBody;

/**
 * Implementacia DAO rozhrania pre manipulaciu s notifikovanymi osobami 
 * 
 * {@link cz.nlfnorm.dao.NotifiedBodyDao}
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("notifiedBody")
public class NotifiedBodyDaoImpl extends BaseDaoImpl<NotifiedBody, Long> implements NotifiedBodyDao {
	
	public NotifiedBodyDaoImpl(){
		super(NotifiedBody.class);
	}
	
	
	
	/**
	 * Skontroluje, ci moze byt dana notofikovana osoba odstranena zo systemu,
	 * resp. ci nie je pouziva v norme
	 * 
	 * @param notifiedBody
	 * @return TRUE, v pripade ak moze byt dostranena, inak FALSE
	 */
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

	
	/**
	 * Skontroluje, ci je evidovana notifikovana osoba s danym nazvom
	 * 
	 * @param String nazov NO
	 * @param id danej osoby, alebo 0
	 * @return TRUE, ak nie je evidovana, inak FALSE
	 */
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

	
	/**
	 * Vrati zoznam notifikovanych osob zoskupeny podla evidovanych krajin
	 * 
	 * @param Boolean enabled Ak je true, vratia sa len publikovane, resp. false nepublikovane
	 * @return List<NotifiedBody> zoznam notifkovanych osob
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifiedBody> getNotifiedBodiesGroupedByCountry(Boolean enabled) {
		StringBuilder hql = new StringBuilder("FROM NotifiedBody nb ");
		if(enabled != null){
			hql.append(" WHERE nb.enabled=:enabled ");
		}
		hql.append("ORDER BY nb.country.id");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(enabled != null){
			query.setBoolean("enabled", enabled);
		}
		return (List<NotifiedBody>)query.list();
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<NotifiedBody> autocomplete(final String term, final Boolean enabled) {
		StringBuilder hql = new StringBuilder("select n.id, n.name, n.aoCode, n.noCode from NotifiedBody n");
		hql.append(" where ( unaccent(lower(n.name)) like CONCAT('', unaccent(:query), '%') ");
		hql.append(" or unaccent(lower(n.aoCode)) like CONCAT('%', unaccent(:query), '%') ");
		hql.append(" or unaccent(lower(n.noCode)) like CONCAT('%', unaccent(:query), '%') ) ");
		
		if(enabled != null){
			hql.append(" AND n.enabled=:enabled");
		}
		
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql.toString());
		
		if(enabled != null){
			hqlQuery.setBoolean("enabled", enabled);
		}
		return hqlQuery.setString("query",  term )
				.setMaxResults(8)
				.list();
	}

}
