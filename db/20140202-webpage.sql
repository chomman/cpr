begin;
	ALTER TABLE webpage DROP COLUMN keywords;
	ALTER TABLE webpage ADD COLUMN bottom_text_english text;
	ALTER TABLE webpage ADD COLUMN description_english character varying(255);
	ALTER TABLE webpage ADD COLUMN name_english character varying(100);
	ALTER TABLE webpage ADD COLUMN title_english character varying(150);
	ALTER TABLE webpage ADD COLUMN top_text_english text;
end;