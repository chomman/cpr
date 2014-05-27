package cz.nlfnorm.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.services.PortalUserService;

@EnableScheduling
@Service("schedulingService")
@Transactional(propagation = Propagation.REQUIRED)
public class SchedulingServiceImpl {
	
	@Autowired
	private PortalUserService portalUserService;
	
	private static final Logger logger = Logger.getLogger(SchedulingServiceImpl.class);
	
	@Scheduled(cron = "0 0 5 1/1 * ? *")
	public void sendEmailAlerts() {
		logger.info("Starti send emails..");
		portalUserService.sendEmailAlerts();
	}

	
}
