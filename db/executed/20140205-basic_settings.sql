begin;

	ALTER TABLE basic_settings ADD COLUMN header_title_english character varying(255);
	ALTER TABLE basic_settings ADD COLUMN owner_name_english character varying(255);
	ALTER TABLE basic_settings ADD COLUMN system_name_english character varying(255);
	UPDATE basic_settings SET header_title_english='Product regulation', owner_name_english='Institute for testing and certification, inc.', system_name_english='Information system';
end;