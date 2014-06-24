package cz.nlfnorm.quasar.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.QuasarSettingsDao;
import cz.nlfnorm.quasar.entities.QuasarSettings;
import cz.nlfnorm.quasar.services.QuasarSettingsService;

@Service("quasarSettingsService")
@Transactional
public class QuasarSettingsServiceImpl implements QuasarSettingsService {
	
	@Autowired
	private QuasarSettingsDao quasarSettingsDao;
	
	@Override
	public void update(final QuasarSettings quasarSettings) {
		quasarSettingsDao.update(quasarSettings);
	}

	@Override
	@Transactional(readOnly = true)
	public QuasarSettings getSettings() {
		return quasarSettingsDao.getByID(QuasarSettings.SETTINGS_ID);
	}

}
