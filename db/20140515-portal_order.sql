begin;
	ALTER TABLE user_info ADD COLUMN portal_country character varying(2);
	UPDATE user_info SET portal_country='CR';
	ALTER TABLE user_info ALTER COLUMN  portal_country SET NOT NULL;
	
	
	ALTER TABLE portal_order ADD COLUMN portal_country character varying(2);
	UPDATE portal_order SET portal_country='CR';
	ALTER TABLE portal_order ALTER COLUMN  portal_country SET NOT NULL;
	
	DROP SEQUENCE essential_characteristic_id_seq;
	DROP SEQUENCE tag_id_seq;
	DROP SEQUENCE declaration_of_performance_id_seq;
end;