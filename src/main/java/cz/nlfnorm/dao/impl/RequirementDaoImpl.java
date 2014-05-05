package cz.nlfnorm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.RequirementDao;
import cz.nlfnorm.entities.Country;
import cz.nlfnorm.entities.Requirement;
import cz.nlfnorm.entities.Standard;

/**
 * Implementacia DAO rozhrania {@link cz.nlfnorm.dao.RequirementDao}
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
