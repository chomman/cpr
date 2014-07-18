package cz.nlfnorm.quasar.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.quasar.dao.DossierReportItemDao;
import cz.nlfnorm.quasar.entities.DossierReportItem;
import cz.nlfnorm.quasar.services.DossierReportItemService;

@Transactional
@Service("dossierReportItemService")
public class DossierReportItemServiceImpl implements DossierReportItemService {

	@Autowired
	private DossierReportItemDao  dossierReportItemDao;
	
	@Override
	public void create(final DossierReportItem dossierReportItem) {
		dossierReportItemDao.save(dossierReportItem);
	}

	@Override
	public void update(final DossierReportItem dossierReportItem) {
		dossierReportItemDao.update(dossierReportItem);
	}

	@Override
	public void delete(final DossierReportItem dossierReportItem) {
		dossierReportItemDao.remove(dossierReportItem);
	}

	@Override
	@Transactional(readOnly = true)
	public DossierReportItem getById(final long id) {
		return dossierReportItemDao.getByID(id);
	}

	@Override
	public void createOrUpdate(final DossierReportItem dossierReportItem) {
		if(dossierReportItem.getId() == null){
			create(dossierReportItem);
		}else{
			update(dossierReportItem);
		}
	}

}
