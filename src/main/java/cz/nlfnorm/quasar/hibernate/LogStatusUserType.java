package cz.nlfnorm.quasar.hibernate;

import cz.nlfnorm.quasar.enums.LogStatus;

public class LogStatusUserType extends PersistentEnumUserType<LogStatus>{

	@Override
	public Class<LogStatus> returnedClass() {
		return LogStatus.class;
	}

}
