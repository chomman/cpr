package cz.nlfnorm.quasar.services.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.nlfnorm.entities.User;
import cz.nlfnorm.quasar.dao.NandoCodeDao;
import cz.nlfnorm.quasar.entities.Auditor;
import cz.nlfnorm.quasar.entities.NandoCode;
import cz.nlfnorm.quasar.services.NandoCodeService;
import cz.nlfnorm.utils.UserUtils;

@Service("nandoCodeService")
@Transactional(propagation = Propagation.REQUIRED)
public class NandoCodeServiceImpl implements NandoCodeService {
	
	@Autowired
	private NandoCodeDao nandoCodeDao;

	@Override
	@Transactional(readOnly = true)
	public NandoCode getById(final Long id) {
		return nandoCodeDao.getByID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public NandoCode getByNandoCode(final String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getAll() {
		return nandoCodeDao.getAll();
	}

	@Override
	public void delete(final NandoCode nandoCode) {
		nandoCodeDao.remove(nandoCode);
	}

	@Override
	public void update(final NandoCode nandoCode) {
		nandoCodeDao.update(nandoCode);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getFirstLevelCodes() {
		return nandoCodeDao.getFirstLevelCodes();
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getSecondLevelCodes() {
		return nandoCodeDao.getSecondLevelCodes();
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getForProductAssessorA() {
		return nandoCodeDao.getCodesForAuditorType(Auditor.TYPE_PRODUCT_ASSESSOR_A, true);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getForProductAssessorR() {
		return nandoCodeDao.getCodesForAuditorType(Auditor.TYPE_PRODUCT_ASSESSOR_R, true);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NandoCode> getForProductSpecialist() {
		return nandoCodeDao.getCodesForAuditorType(Auditor.TYPE_PRODUCT_SPECIALIST, true);
		
	}

	@Override
	public void createOrUpdate(final NandoCode nandoCode) {
		final User user = UserUtils.getLoggedUser();
		nandoCode.setChangedBy(user);
		nandoCode.setChanged(new LocalDateTime());
		if(nandoCode.getId() == null){
			create(nandoCode);
		}else{
			update(nandoCode);
		}
	}

	@Override
	public void create(final NandoCode nandoCode) {
		nandoCodeDao.save(nandoCode);
	}
	
	
	
}
