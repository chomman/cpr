begin;
	
	 CREATE SEQUENCE quasar_company_id_seq
		  INCREMENT 1
		  MINVALUE 1
		  MAXVALUE 9223372036854775807
		  START 10
		  CACHE 1;

	CREATE TABLE quasar_company
	(
	  id bigint NOT NULL,
	  name character varying(60) NOT NULL,
	  CONSTRAINT quasar_company_pkey PRIMARY KEY (id),
	  CONSTRAINT uk_qf9puqfq1ykykdlyk353v8q8a UNIQUE (name)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE quasar_company
	  OWNER TO itcuser;
  
	  CREATE TABLE quasar_certification_body
		(
		  id bigint NOT NULL,
		  name character varying(60) NOT NULL,
		  CONSTRAINT quasar_certification_body_pkey PRIMARY KEY (id),
		  CONSTRAINT uk_8c49a82lqypu686crn7qaobpi UNIQUE (name)
		)
		WITH (
		  OIDS=FALSE
		);
		ALTER TABLE quasar_certification_body
		  OWNER TO itcuser;
		  
	insert into quasar_company (id,name) VALUES (1,'Institut pro testování a certifikaci, a. s.');
	insert into quasar_certification_body (id,name) VALUES (1,'ITC');
end;