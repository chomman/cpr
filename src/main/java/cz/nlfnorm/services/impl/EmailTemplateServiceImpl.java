package cz.nlfnorm.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.EmailTemplateDao;
import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.utils.UserUtils;

@Service("emailTemplateService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	private EmailTemplateDao emailTemplateDao;
	
	@Override
	public void create(final EmailTemplate emailTempalate) {
		emailTemplateDao.save(emailTempalate);
	}

	@Override
	public void update(final EmailTemplate emailTempalate) {
		emailTemplateDao.update(emailTempalate);
		
	}

	@Override
	@Transactional(readOnly = true)
	public EmailTemplate getById(final Long id) {
		return emailTemplateDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public EmailTemplate getByCode(final String code) {
		return emailTemplateDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmailTemplate> getAll() {
		return emailTemplateDao.getAll();
	}

	@Override
	public void createOrUpdate(EmailTemplate emailTemplate) {
		final User user = UserUtils.getLoggedUser();
		emailTemplate.setChanged(new LocalDateTime());
		emailTemplate.setChangedBy(user);
		if(emailTemplate.getId() == null){
			emailTemplate.setCreated(new LocalDateTime());
			emailTemplate.setCreatedBy(user);
			create(emailTemplate);
		}else{
			update(emailTemplate);
		}
	}

}
