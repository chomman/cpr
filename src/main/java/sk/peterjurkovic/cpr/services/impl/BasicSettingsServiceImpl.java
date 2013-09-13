package sk.peterjurkovic.cpr.services.impl;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.dao.BasicSettingsDao;
import sk.peterjurkovic.cpr.entities.BasicSettings;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.BasicSettingsService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;


@Service("basicSettingsService")
@Transactional(propagation = Propagation.REQUIRED)
public class BasicSettingsServiceImpl implements BasicSettingsService {
	
	@Autowired
	private BasicSettingsDao basicSettingsDao;
	@Autowired
	private UserService userService;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public BasicSettings getBasicSettings() {
		BasicSettings settings = basicSettingsDao.getByID(Constants.BASIC_SETTINGS_DB_ID);
		if(settings == null){
			throw new IllegalArgumentException("Základní nastavení pod hodnotou ID: " + 
								Constants.BASIC_SETTINGS_DB_ID + " sa v systému nenachází.");
		}
		return settings;
	}

	
	
	@Override
	public void updateBasicSettings(BasicSettings basicSettings) {
		User user = userService.getUserByUsername(UserUtils.getLoggedUser().getUsername());
		basicSettings.setChangedBy(user);
		basicSettings.setChanged(new LocalDateTime());
		basicSettingsDao.merge(basicSettings);
	}

}
