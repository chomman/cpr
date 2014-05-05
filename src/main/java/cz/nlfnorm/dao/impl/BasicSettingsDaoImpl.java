package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.BasicSettingsDao;
import cz.nlfnorm.entities.BasicSettings;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Repository("basicSettings")
public class BasicSettingsDaoImpl extends BaseDaoImpl<BasicSettings, Long> implements BasicSettingsDao{
	
	public BasicSettingsDaoImpl(){
		super(BasicSettings.class);
	}
}
