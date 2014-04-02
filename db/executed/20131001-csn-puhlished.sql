begin;
	ALTER TABLE csn DROP COLUMN published;
    ALTER TABLE csn ADD COLUMN published date;
    ALTER TABLE csn DROP COLUMN bulletin;
    ALTER TABLE basic_settings ADD COLUMN version character varying(30);
end;