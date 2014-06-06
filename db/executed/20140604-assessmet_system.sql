begin;
	ALTER TABLE assessment_system DROP COLUMN dop_text;
	ALTER TABLE assessment_system ADD COLUMN description_english text;
	ALTER TABLE assessment_system ADD COLUMN english_name character varying(25);
end;