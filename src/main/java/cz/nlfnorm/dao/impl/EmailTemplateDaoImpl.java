package cz.nlfnorm.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.EmailTemplateDao;
import cz.nlfnorm.entities.EmailTemplate;

@Repository("emailTemplateDao")
public class EmailTemplateDaoImpl extends BaseDaoImpl<EmailTemplate, Long> implements EmailTemplateDao{

	public EmailTemplateDaoImpl(){
		super(EmailTemplate.class);
	}
	
}
