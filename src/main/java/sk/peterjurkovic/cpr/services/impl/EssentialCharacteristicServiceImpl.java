package sk.peterjurkovic.cpr.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.peterjurkovic.cpr.dao.EssentialCharacteristicDao;
import sk.peterjurkovic.cpr.entities.EssentialCharacteristic;
import sk.peterjurkovic.cpr.services.EssentialCharacteristicService;


@Service("essentialCharacteristicService")
@Transactional(propagation = Propagation.REQUIRED)
public class EssentialCharacteristicServiceImpl implements EssentialCharacteristicService{

	@Autowired
	private EssentialCharacteristicDao essentialCharacteristicDao;
	
	@Override
	@Transactional(readOnly = true)
	public EssentialCharacteristic getEssentialCharacteristicById(Long id) {
		return essentialCharacteristicDao.getByID(id);
	}

}
