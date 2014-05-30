package cz.nlfnorm.services.impl;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@EnableScheduling
@Service("schedulingService")
@Transactional(propagation = Propagation.REQUIRED)
public class SchedulingServiceImpl {
	
	
	
}
