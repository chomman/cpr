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

end;