package sk.peterjurkovic.cpr.dao;

import sk.peterjurkovic.cpr.entities.BasicRequirement;


public interface BasicRequirementDao extends BaseDao<BasicRequirement, Long> {

	boolean isNameUniqe(String name, Long id);
}