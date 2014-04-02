begin;
	ALTER TABLE standard_csn_has_change RENAME COLUMN csnonline_id TO csn_online_id;
	ALTER TABLE standard_csn_has_change RENAME COLUMN change_date TO status_date;
	ALTER TABLE standard_csn_has_change RENAME COLUMN change_note TO note;
	ALTER TABLE standard_csn_has_change RENAME COLUMN change_code TO csn_name;
	
	ALTER TABLE standard_has_notified_body ADD COLUMN id bigint;
	ALTER TABLE standard_has_notified_body ADD COLUMN assignment_date date;
	
	CREATE SEQUENCE standard_has_notified_body_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;
	  
	update standard_has_notified_body set id = NEXTVAL('standard_has_notified_body_id_seq');
	ALTER TABLE essential_characteristic ALTER COLUMN id SET NOT NULL;
end;