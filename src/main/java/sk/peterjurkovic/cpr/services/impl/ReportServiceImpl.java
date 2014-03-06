package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.ReportDao;
import sk.peterjurkovic.cpr.entities.Report;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.ReportService;
import sk.peterjurkovic.cpr.services.UserService;
import sk.peterjurkovic.cpr.utils.UserUtils;

@Service("reportService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private UserService userService;
	
	
	@Override
	public void create(Report report) {
		reportDao.save(report);
	}

	@Override
	public void delete(Report report) {
		reportDao.remove(report);
	}

	@Override
	public void update(Report report) {
		reportDao.update(report);
	}


	@Override
	@Transactional(readOnly = true)
	public Report getById(final Long id) {
		return reportDao.getByID(id);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<Report> getAll() {
		return reportDao.getAll();
	}

	
	@Override
	public void createOrUpdate(Report report) {
		Validate.notNull(report);
		report.setChanged(new LocalDateTime());
		final User user = UserUtils.getLoggedUser();
		report.setChangedBy(user);
		if(report.getId() == null){ 
			report.setCreated(new LocalDateTime());
			report.setCreatedBy(user);
			create(report);
		}else{
			update(report);
		}
	}
	
	
	
}