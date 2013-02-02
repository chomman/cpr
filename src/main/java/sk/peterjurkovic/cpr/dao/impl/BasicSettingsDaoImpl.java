package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.BasicSettingsDao;
import sk.peterjurkovic.cpr.entities.BasicSettings;

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
