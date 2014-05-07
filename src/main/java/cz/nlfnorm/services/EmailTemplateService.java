package cz.nlfnorm.services;

import java.util.List;

import cz.nlfnorm.entities.EmailTemplate;

public interface EmailTemplateService {
	
	void create(EmailTemplate emailTempalate);
	
	void update(EmailTemplate emailTempalate);
	
	EmailTemplate getById(Long id);
	
	EmailTemplate getByCode(String code);
	
	List<EmailTemplate> getAll();
	
	void createOrUpdate(EmailTemplate emailTemplate);
	
}
