package cz.nlfnorm.hibernate;

import org.hibernate.dialect.PostgreSQL82Dialect;

public class NlfSqlDialect extends PostgreSQL82Dialect {

	public NlfSqlDialect(){
		super();
		registerFunction("fulltext_search", new PostgreSQLFullTextSearchFunction());
	}
}
