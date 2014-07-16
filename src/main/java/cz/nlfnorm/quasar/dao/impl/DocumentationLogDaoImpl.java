package cz.nlfnorm.quasar.dao.impl;

import org.springframework.stereotype.Repository;

import cz.nlfnorm.quasar.dao.DocumentationLogDao;
import cz.nlfnorm.quasar.entities.DocumentationLog;

@Repository("documentationLogDao")
public class DocumentationLogDaoImpl extends AbstractLogDaoImpl<DocumentationLog> implements DocumentationLogDao{
	
	public DocumentationLogDaoImpl(){
		super(DocumentationLog.class);
	}

}
