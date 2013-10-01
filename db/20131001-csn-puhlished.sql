begin;
	ALTER TABLE csn DROP COLUMN published;
    ALTER TABLE csn ADD COLUMN published date;
    ALTER TABLE csn DROP COLUMN bulletin;
end;