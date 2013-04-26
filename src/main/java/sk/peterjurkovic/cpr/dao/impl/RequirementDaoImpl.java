package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.RequirementDao;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

/**
 * Implementacia DAO rozhrania {@link sk.peterjurkovic.cpr.dao.RequirementDao}
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("requirementDao")
public class RequirementDaoImpl extends BaseDaoImpl<Requirement, Long> implements RequirementDao{

	public RequirementDaoImpl() {
		super(Requirement.class);
	}
	
	/**
	 * Vrati zoznam poziadavko na pre danu krainu a normu
	 * 
	 * @param Country krajina
	 * @param Standar dana norma pre kotru maju byt vratene poziadavky
	 * @return List<Requirement> zoznam poziadavkov
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Requirement> getRequirementsByCountryAndStandard(
			Country country, Standard standard) {
		return (List<Requirement>)sessionFactory.getCurrentSession()
				.createQuery("FROM Requirement r WHERE country =:country AND standard=:standard")
				.setEntity("country", country)
				.setEntity("standard", standard)
				.list();
	}

}
