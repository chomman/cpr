package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.QuasarSettingsDao;
import cz.nlfnorm.quasar.entities.QuasarSettings;

/**
 * 
 * @author Peter Jurkovic
 * @date Jun 24, 2014
 */
@Repository("quasarSettingsDao")
public class QuasarSettingsDaoImpl extends BaseDaoImpl<QuasarSettings, Long> implements QuasarSettingsDao{

	public QuasarSettingsDaoImpl(){
		super(QuasarSettings.class);
	}
	
}
