begin;
	ALTER TABLE webpage_content ADD COLUMN webpage_tsvector TSVECTOR;
	UPDATE webpage_content 
	set webpage_tsvector = 	to_tsvector(cast(localized_key as regconfig), concat(title, ' ', content, ' ', unaccent(title), ' ', unaccent(content), ' ', unaccent(description), ' ', unaccent(description) ));
end;