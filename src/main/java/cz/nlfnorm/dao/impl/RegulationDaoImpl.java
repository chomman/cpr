package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.RegulationDao;
import cz.nlfnorm.entities.Regulation;

/**
 * DAO implementation of {@link RegulationDao} 
 * 
 * @author Peter Jurkovic
 * @date Aug 18, 2014
 */
@Repository("regulationDao")
public class RegulationDaoImpl extends BaseDaoImpl<Regulation, Long> implements RegulationDao{
	
	public RegulationDaoImpl(){
		super(Regulation.class);
	}
	
}
