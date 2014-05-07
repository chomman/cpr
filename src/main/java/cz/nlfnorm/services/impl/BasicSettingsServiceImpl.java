package cz.nlfnorm.services.impl;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.constants.Constants;
import cz.nlfnorm.dao.BasicSettingsDao;
import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.UserService;
import cz.nlfnorm.utils.UserUtils;


@Service("basicSettingsService")
@Transactional(propagation = Propagation.REQUIRED)
public class BasicSettingsServiceImpl implements BasicSettingsService {
	
	@Autowired
	private BasicSettingsDao basicSettingsDao;
	@Autowired
	private UserService userService;
	@Value("#{config['csnonlineurl']}")
	private String csnOnlineUrl;
	
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


	@Override
	public String getCsnOnlineUrl() {
		if(csnOnlineUrl == null){
			return "";
		}
		return csnOnlineUrl;
	}

}
