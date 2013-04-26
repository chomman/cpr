package sk.peterjurkovic.cpr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.MandateDao;
import sk.peterjurkovic.cpr.entities.Mandate;

/**
 * Implementacia rozhrania pre manipulaicu s mandatom
 * 
 * @see sk.peterjurkovic.cpr.dao.MandateDao
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("mandateDao")
public class MandateDaoImpl extends BaseDaoImpl<Mandate, Long> implements MandateDao {
	
	public MandateDaoImpl(){
		super(Mandate.class);
	}

	
	/**
	 * Vrati stranku mandatov
	 * 
	 * @param Long cislo stranky
	 * @return List<Mandate>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Mandate> getMandatePage(int pageNumber) {
		
		List<Mandate> mandates = new ArrayList<Mandate>();
		mandates = sessionFactory.getCurrentSession()
				.createQuery("from Mandate m")
				.setFirstResult(Constants.ADMIN_PAGINATION_PAGE_SIZE * ( pageNumber -1))
				.setMaxResults(Constants.ADMIN_PAGINATION_PAGE_SIZE)
				.list();

		return mandates;
	}

	
	/**
	 * Vrati celkovy pocet evidovanych mandatov.
	 * 
	 * @return Long 
	 */
	@Override
	public Long getCountOfMandates() {
		return (Long) sessionFactory.getCurrentSession()
						.createQuery("SELECT count(*) FROM Mandate")
						.uniqueResult();

	}
	
	/**
	 * Skontroluje, ci moze byt dany mandat odstraneny, resp.
	 * ci sa nenachadza v norme.
	 * 
	 * @param Mandate dany mandat
	 * @return TRUE, ak moze byt odstraneny, inak FALSE
	 */
	@Override
	public boolean canBeDeleted(final Mandate mandate) {
		StringBuilder hql = new StringBuilder("SELECT count(*) FROM Standard s");
		hql.append(" WHERE :mandate IN ELEMENTS(s.mandates)");
		Long result = (Long)sessionFactory.getCurrentSession()
						.createQuery(hql.toString())
						.setEntity("mandate", mandate)
						.uniqueResult();
		return (result == 0);
	}
	
}
