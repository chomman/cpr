begin;

	
	CREATE SEQUENCE quasar_nando_code_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 100000
	  START 1
	  CACHE 1;
	  
	CREATE SEQUENCE quasar_eac_code_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 100000
	  START 1
	  CACHE 1;
	  
	CREATE SEQUENCE quasar_partner_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 10000
	  START 1
	  CACHE 1;
	  
	CREATE SEQUENCE quasar_field_of_education_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 1000000
	  START 1
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_special_training_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 1000000
	  START 1
	  CACHE 1;

end;