package sk.peterjurkovic.cpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.NotifiedBodyDao;
import sk.peterjurkovic.cpr.entities.NotifiedBody;
import sk.peterjurkovic.cpr.services.NotifiedBodyService;

@Service("notifiedBodyService")
@Transactional(propagation = Propagation.REQUIRED)
public class NotifiedBodyServiceImpl implements NotifiedBodyService {

	@Autowired
	private NotifiedBodyDao notifiedBodyDao;
	
	@Override
	public void createNotifiedBody(NotifiedBody notifiedBody) {
		notifiedBodyDao.save(notifiedBody);
	}

	@Override
	public void updateNotifiedBody(NotifiedBody notifiedBody) {
		notifiedBodyDao.update(notifiedBody);
	}

	@Override
	public void deleteNotifiedBody(NotifiedBody notifiedBody) {
		notifiedBodyDao.remove(notifiedBody);
	}

	@Override
	@Transactional(readOnly = true)
	public NotifiedBody getNotifiedBodyById(Long id) {
		return notifiedBodyDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public NotifiedBody getNotifiedBodyByCode(String code) {
		return notifiedBodyDao.getByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotifiedBody> getAllNotifiedBodies() {
		return notifiedBodyDao.getAll();
	}

}
