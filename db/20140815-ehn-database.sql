begin;
	CREATE SEQUENCE shared_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	 ALTER TABLE standard DROP COLUMN is_cumulative;
	 ALTER TABLE standard DROP COLUMN replaced_standard_code;
end;