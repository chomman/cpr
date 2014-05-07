begin; 

	ALTER TABLE basic_settings DROP COLUMN csn_online_url;
	ALTER TABLE basic_settings DROP COLUMN google_analytics_code;
	ALTER TABLE basic_settings DROP COLUMN header_title;
	ALTER TABLE basic_settings DROP COLUMN owner_name;
	ALTER TABLE basic_settings DROP COLUMN system_name;
	ALTER TABLE basic_settings DROP COLUMN header_title_english;
	ALTER TABLE basic_settings DROP COLUMN owner_name_english;
	ALTER TABLE basic_settings DROP COLUMN system_name_english;
	
end;