package sk.peterjurkovic.cpr.services;

import java.util.List;

import sk.peterjurkovic.cpr.entities.NotifiedBody;

public interface NotifiedBodyService {
	
	void createNotifiedBody(NotifiedBody notifiedBody);
	
	void updateNotifiedBody(NotifiedBody notifiedBody);
	
	void deleteNotifiedBody(NotifiedBody notifiedBody);
	
	NotifiedBody getNotifiedBodyById(Long id);
	
	NotifiedBody getNotifiedBodyByCode(String code);
	
	List<NotifiedBody> getAllNotifiedBodies();
	
	void saveOrUpdateNotifiedBody(NotifiedBody notifiedBody);
	
	boolean canBeDeleted(NotifiedBody notifiedBody);
	
	boolean isNotifiedBodyNameUniqe(String name, Long id);
}
