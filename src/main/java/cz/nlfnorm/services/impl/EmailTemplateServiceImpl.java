package cz.nlfnorm.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.dao.EmailTemplateDao;
import cz.nlfnorm.entities.BasicSettings;
import cz.nlfnorm.entities.EmailTemplate;
import cz.nlfnorm.entities.User;
import cz.nlfnorm.mail.HtmlMailMessage;
import cz.nlfnorm.mail.NlfnormMailSender;
import cz.nlfnorm.services.BasicSettingsService;
import cz.nlfnorm.services.EmailTemplateService;
import cz.nlfnorm.utils.UserUtils;
import cz.nlfnorm.utils.ValidationsUtils;

@Service("emailTemplateService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	private EmailTemplateDao emailTemplateDao;
	@Autowired
	private BasicSettingsService basicSettingsService;
	@Autowired
	private NlfnormMailSender nlfnormMailSender;
	
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

	@Override
	public boolean isEmailTemplateValid(final EmailTemplate emailTemplate) {
		return getTestHtmlMailMessage(emailTemplate).isContentValid();
	}
		
	@Override
	public void sendTestEmailTo(final String emailAddress, Long templateId) {
		Validate.notNull(emailAddress);
		if(ValidationsUtils.isEmailValid(emailAddress)){
			final EmailTemplate emailTemplate = getById(templateId);
			Validate.notNull(emailTemplate);
			HtmlMailMessage message = getTestHtmlMailMessage(emailTemplate);
			message.addRecipientTo(emailAddress);
			nlfnormMailSender.send(message);
		}
	}
	
	private HtmlMailMessage getTestHtmlMailMessage(final EmailTemplate emailTemplate){
		final BasicSettings settings = basicSettingsService.getBasicSettings();
		HtmlMailMessage message = new HtmlMailMessage(
				settings.getSystemEmail(), 
				emailTemplate.getSubject(), 
				emailTemplate.getBody(), 
				prepareValidationContextFor(emailTemplate)
		);
		return message;
	}
	
	
	
	private Map<String, Object> prepareValidationContextFor(EmailTemplate emailTemplate){
		String[] variableList = emailTemplate.getAvaiableVariables();
		if(variableList != null){
			Map<String, Object> context = new HashMap<String, Object>();
			for(String varable : variableList){
				context.put(varable, "hodnota-" + varable);
			}
			return context;
		}
		return null;
	}

	

}
