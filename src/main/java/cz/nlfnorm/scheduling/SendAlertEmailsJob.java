package cz.nlfnorm.scheduling;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SendAlertEmailsJob extends QuartzJobBean {
	
	private final Logger logger =  Logger.getLogger(getClass());
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		logger.info("Sending alert emails...");
	}

}
