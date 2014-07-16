package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.dao.impl.BaseDaoImpl;
import cz.nlfnorm.quasar.dao.DocumentationLogDao;
import cz.nlfnorm.quasar.entities.DocumentationLog;

@Repository("documentationLogDao")
public class DocumentationLogDaoImpl extends BaseDaoImpl<DocumentationLog, Long> implements DocumentationLogDao{
	
	public DocumentationLogDaoImpl(){
		super(DocumentationLog.class);
	}

}
