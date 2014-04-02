begin;

	CREATE SEQUENCE localized_webpage_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;
	  
	DROP SEQUENCE webpage_category_id_seq;
	DROP TABLE webpage CASCADE;
	DROP TABLE webpage_category CASCADE;
	DROP TABLE webpage_content CASCADE;
	DROP TABLE tag;
	
	
end;