package cz.nlfnorm.scheduling;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public class SchedulingServiceImpl {
	
	private static final Logger logger = Logger.getLogger(SchedulingServiceImpl.class);
		
	public void sendEmailAlerts() {
		logger.info("Starti send emails..");
	}

	
}
