package sk.peterjurkovic.cpr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.RequirementDao;
import sk.peterjurkovic.cpr.entities.Country;
import sk.peterjurkovic.cpr.entities.Requirement;
import sk.peterjurkovic.cpr.entities.Standard;

@Repository("requirementDao")
public class RequirementDaoImpl extends BaseDaoImpl<Requirement, Long> implements RequirementDao{

	public RequirementDaoImpl() {
		super(Requirement.class);
	}

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
