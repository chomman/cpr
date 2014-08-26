begin;
	CREATE SEQUENCE shared_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	 
	  CREATE TABLE regulation
		(
		  id bigint NOT NULL,
		  code character varying(20) NOT NULL,
		  CONSTRAINT regulation_pkey PRIMARY KEY (id),
		  CONSTRAINT uk_96iqqac9fmllwcqw7ocq23rfw UNIQUE (code)
		)
		WITH (
		  OIDS=FALSE
		);
		
		CREATE TABLE regulation_content
		(
		  regulation_id bigint NOT NULL,
		  description_czech text,
		  nameczech character varying(100),
		  nameenglish character varying(100),
		  pdf_czech character varying(150),
		  pdf_english character varying(150),
		  localized_key character varying(255) NOT NULL,
		  description_english text,
		  CONSTRAINT regulation_content_pkey PRIMARY KEY (regulation_id, localized_key),
		  CONSTRAINT fk_nv88be0u7snrm2gkslmw0ui5u FOREIGN KEY (regulation_id)
		      REFERENCES regulation (id) MATCH SIMPLE
		      ON UPDATE NO ACTION ON DELETE NO ACTION
		)
		WITH (
		  OIDS=FALSE
		);

	CREATE TABLE standard_category
		(
		  id bigint NOT NULL,
		  code character varying(10),
		  name_czech character varying(120) NOT NULL,
		  name_english character varying(120),
		  nando_url character varying(250),
		  ojeu_czech text,
		  ojeu_english text,
		  specialization_czech character varying(120),
		  specialization_english character varying(120),
		  CONSTRAINT standard_category_pkey PRIMARY KEY (id)
		)
		WITH (
		  OIDS=FALSE
		);
		
	CREATE TABLE standard_category_has_regulations
	(
	  standard_category_id bigint NOT NULL,
	  regulation_id bigint NOT NULL,
	  CONSTRAINT standard_category_has_regulations_pkey PRIMARY KEY (standard_category_id, regulation_id),
	  CONSTRAINT fk_cv97t46bfmxi1u10dpmq57fdc FOREIGN KEY (regulation_id)
	      REFERENCES regulation (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_m2pysevgxlena5939o45qkgbr FOREIGN KEY (standard_category_id)
	      REFERENCES standard_category (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);	
	  
	 ALTER TABLE standard DROP COLUMN is_cumulative;
	 ALTER TABLE standard DROP COLUMN replaced_standard_code;
	 ALTER TABLE standard ADD COLUMN standard_category_id bigint;
	 ALTER TABLE standard ALTER COLUMN czech_name TYPE character varying(550);
	 ALTER TABLE standard ALTER COLUMN english_name TYPE character varying(550);
	 DROP TABLE basic_requirement;
	 DROP TABLE requirement;
	 update standard set standard_category_id = 84;
end;