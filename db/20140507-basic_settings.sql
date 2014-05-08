begin; 

	ALTER TABLE basic_settings DROP COLUMN csn_online_url;
	ALTER TABLE basic_settings DROP COLUMN google_analytics_code;
	ALTER TABLE basic_settings DROP COLUMN header_title;
	ALTER TABLE basic_settings DROP COLUMN owner_name;
	ALTER TABLE basic_settings DROP COLUMN system_name;
	ALTER TABLE basic_settings DROP COLUMN header_title_english;
	ALTER TABLE basic_settings DROP COLUMN owner_name_english;
	ALTER TABLE basic_settings DROP COLUMN system_name_english;
	
	ALTER TABLE basic_settings ADD COLUMN city character varying(50);
	ALTER TABLE basic_settings ADD COLUMN company_name character varying(100);
	ALTER TABLE basic_settings ADD COLUMN cz_account_number character varying(25);
	ALTER TABLE basic_settings ADD COLUMN cz_iban character varying(34);
	ALTER TABLE basic_settings ADD COLUMN cz_swift character varying(8);
	ALTER TABLE basic_settings ADD COLUMN dic character varying(10);
	ALTER TABLE basic_settings ADD COLUMN eu_account_number character varying(25);
	ALTER TABLE basic_settings ADD COLUMN eu_iban character varying(34);
	ALTER TABLE basic_settings ADD COLUMN eu_swift character varying(8);
	ALTER TABLE basic_settings ADD COLUMN ico character varying(10);
	ALTER TABLE basic_settings ADD COLUMN invoice_email character varying(35);
	ALTER TABLE basic_settings ADD COLUMN phone character varying(20);
	ALTER TABLE basic_settings ADD COLUMN street character varying(80);
	ALTER TABLE basic_settings ADD COLUMN zip character varying(6);

	
	CREATE SEQUENCE email_template_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 10000
	  START 1
	  CACHE 1;
	  
	  
	CREATE TABLE email_template
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  code character varying(255),
  created timestamp without time zone,
  enabled boolean,
  body text,
  name character varying(100),
  subject character varying(150),
  variables character varying(300),
  variable_description text,
  id_user_changed_by bigint,
  id_user_created_by bigint,
  CONSTRAINT email_template_pkey PRIMARY KEY (id),
  CONSTRAINT fk_9bdjqgopmkq9cl70fbwr39a6j FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_f8eis4obuupn0wgi3pbh1qsth FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE email_template
  OWNER TO postgres;
		  
end;